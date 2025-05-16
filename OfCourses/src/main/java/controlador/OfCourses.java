package controlador;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jakarta.persistence.EntityManager;
import javafx.scene.image.Image;
import modelo.Curso;
import modelo.Leccion;
import modelo.Pregunta;
import modelo.Usuario;
import persistencia.CursoRepository;
import persistencia.JPAUtil;
import persistencia.LeccionRepository;
import persistencia.PreguntaRepository;
import persistencia.UsuarioRepository;
import servicios.CargadorYAML;

/**
 * Clase principal del controlador de la aplicación, implementada con el patrón Singleton.
 * 
 * Se encarga de gestionar usuarios, cursos, lecciones y preguntas, así como de coordinar la lógica
 * de login, registro, progreso, rachas y recargas.
 */
public class OfCourses {

    /** Ruta de la imagen de perfil por defecto. */
    public static final String FOTO_DEFECTO = "/imagenes/foto-perfil-default.png";

    private static OfCourses unicaInstancia = null;

    private UsuarioRepository repoUser;
    private CursoRepository repoCurso;
    private LeccionRepository repoLeccion;
    private PreguntaRepository repoPregunta;
    private EntityManager em = JPAUtil.getEntityManager();

    private Usuario usuarioActual;

    /**
     * Constructor privado. Inicializa los repositorios con el EntityManager.
     */
    private OfCourses() {
        repoUser = new UsuarioRepository(em);
        repoCurso = new CursoRepository(em);
        repoLeccion = new LeccionRepository(em);
        repoPregunta = new PreguntaRepository(em);
    }

    /**
     * Devuelve la única instancia del controlador (patrón Singleton).
     *
     * @return única instancia de OfCourses
     */
    public static OfCourses getUnicaInstancia() {
        if (unicaInstancia == null) {
            unicaInstancia = new OfCourses();
        }
        return unicaInstancia;
    }

    /**
     * Intenta iniciar sesión con el usuario y contraseña proporcionados.
     *
     * @param user nombre de usuario o email
     * @param password contraseña
     * @return {@code true} si el login es correcto; {@code false} en caso contrario
     */
    public boolean Login(String user, String password) {
        if (user.contains("@")) {
            usuarioActual = repoUser.buscarPorEmail(user);
        } else {
            usuarioActual = repoUser.buscarPorNombre(user);
        }

        if (usuarioActual == null || !usuarioActual.getPassword().equals(password)) {
            usuarioActual = null;
            return false;
        } else {
            usuarioActual.comprobarRacha();
            usuarioActual.recargarSiEsNuevoDia();
            return true;
        }
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param username nombre de usuario
     * @param password contraseña
     * @param email correo electrónico
     * @param url ruta de la imagen de perfil
     * @return {@code true} si el usuario fue registrado correctamente
     */
    public boolean registerUser(String username, String password, String email, String url) {
        Usuario user = new Usuario(username, email, password, url);

        if (!repoUser.guardar(user)) {
            return false;
        }

        usuarioActual = user;
        return true;
    }

    /**
     * Devuelve la imagen de perfil del usuario actual.
     * 
     * Si la imagen no se puede cargar, se utiliza una por defecto.
     *
     * @return imagen de perfil
     */
    public Image getFotoUsuarioActual() {
        Image imagen;

        try {
            imagen = new Image(usuarioActual.getFoto());
        } catch (Exception e) {
            imagen = new Image(getClass().getResource(FOTO_DEFECTO).toString());
        }

        return imagen;
    }

    /**
     * Añade un curso al usuario actual desde un archivo YAML.
     *
     * @param file ruta del archivo YAML
     * @return {@code true} si el curso se añadió correctamente
     */
    public boolean addCurso(String file) {
        CargadorYAML cargador = new CargadorYAML();

        try {
            Curso curso = cargador.cargarCursoDesdeArchivo(file);
            usuarioActual.addCurso(curso);

            for (Leccion l : curso.getLecciones()) {
                for (Pregunta p : l.getPreguntas()) {
                    repoPregunta.guardar(p);
                }
                repoLeccion.guardar(l);
            }

            curso.setAutor(usuarioActual);
            repoCurso.guardar(curso);
            repoUser.guardar(usuarioActual);
            curso.aplicarEstrategia();
        } catch (IOException e) {
            return false;
        }

        repoUser.modificarUsuario(usuarioActual);
        return true;
    }

    /**
     * Devuelve la lista de cursos disponibles para el usuario actual.
     *
     * @return lista de cursos
     */
    public List<Curso> getCursosDisponibles() {
        return usuarioActual.getCursos();
    }

    /**
     * Devuelve un curso del usuario actual por su título.
     *
     * @param cursoActual título del curso
     * @return curso correspondiente o {@code null} si no se encuentra
     */
    public Curso getCurso(String cursoActual) {
        return usuarioActual.getCurso(cursoActual);
    }

    /**
     * Indica si una lección ha sido completada.
     *
     * @param leccion lección a comprobar
     * @return {@code true} si está completada; {@code false} en caso contrario
     */
    public boolean isCompletada(Leccion leccion) {
        return leccion.isCompletada();
    }

    /**
     * Devuelve la siguiente pregunta de una lección, y marca la lección como completada si corresponde.
     *
     * @param actual lección actual
     * @return siguiente pregunta o {@code null} si no hay más
     */
    public Pregunta getSiguientePregunta(Leccion actual) {
        Pregunta siguiente = actual.getSiguientePregunta();
        if (siguiente == null) {
            actual.setCompletada(true);
        }
        repoLeccion.guardar(actual);
        return siguiente;
    }

    /**
     * Devuelve el nombre del usuario actualmente logueado.
     *
     * @return nombre de usuario
     */
    public String getNombreUsuario() {
        return usuarioActual.getUsername();
    }

    /**
     * Recarga las vidas del usuario actual si ha pasado un nuevo día.
     */
    public void recargarVidasSiCorresponde() {
        boolean recarga = usuarioActual.recargarSiEsNuevoDia();
        if (recarga) {
            repoUser.modificarUsuario(usuarioActual);
        }
    }

    /**
     * Resta una vida al usuario actual.
     */
    public void perderVida() {
        usuarioActual.perderVida();
        repoUser.modificarUsuario(usuarioActual);
    }

    /**
     * Indica si el usuario actual tiene vidas disponibles.
     *
     * @return {@code true} si tiene vidas; {@code false} si no tiene
     */
    public boolean tieneVidas() {
        return !usuarioActual.estaSinVidas();
    }

    /**
     * Devuelve la cantidad actual de vidas del usuario.
     *
     * @return número de vidas
     */
    public int getVidas() {
        return usuarioActual.getVidas();
    }

    /**
     * Actualiza los datos del usuario actual.
     *
     * @param nuevoNombre nuevo nombre de usuario (opcional)
     * @param nuevaContrasena nueva contraseña (opcional)
     * @param nuevoCorreo nuevo correo electrónico (opcional)
     * @param nuevaFoto archivo de la nueva foto de perfil (opcional)
     */
    public void actualizarUsuario(String nuevoNombre, String nuevaContrasena, String nuevoCorreo, File nuevaFoto) {
        if (nuevoNombre != null) usuarioActual.setUsername(nuevoNombre);
        if (nuevaContrasena != null) usuarioActual.setPassword(nuevaContrasena);
        if (nuevoCorreo != null) usuarioActual.setEmail(nuevoCorreo);
        if (nuevaFoto != null) usuarioActual.setFoto(nuevaFoto.getAbsolutePath());

        repoUser.modificarUsuario(usuarioActual);
    }

    /**
     * Devuelve el correo electrónico del usuario actual.
     *
     * @return email del usuario
     */
    public String getCorreoUsuario() {
        return usuarioActual.getEmail();
    }

    /**
     * Devuelve la racha actual de días activos del usuario.
     *
     * @return racha actual
     */
    public int getRacha() {
        return usuarioActual.getRacha();
    }

    /**
     * Devuelve la mayor racha alcanzada por el usuario.
     *
     * @return racha máxima
     */
    public int getMaximaRacha() {
        return usuarioActual.getRachaMaxima();
    }

    /**
     * Devuelve el número total de preguntas respondidas por el usuario.
     *
     * @return número de preguntas contestadas
     */
    public int preguntasContestadas() {
        return usuarioActual.obtenerPreguntasContestadas();
    }

    /**
     * Devuelve la pregunta actual de una lección.
     *
     * @param actual lección actual
     * @return pregunta actual
     */
    public Pregunta getPreguntaActual(Leccion actual) {
        return actual.getPreguntaActual();
    }
}
