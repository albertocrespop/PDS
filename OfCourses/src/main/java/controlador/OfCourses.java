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

public class OfCourses {
	public static final String FOTO_DEFECTO = "/imagenes/foto-perfil-default.png";
	private static OfCourses unicaInstancia = null;
	
	private UsuarioRepository repoUser;
	private CursoRepository repoCurso;
	private LeccionRepository repoLeccion;
	private PreguntaRepository repoPregunta;
	private EntityManager em = JPAUtil.getEntityManager();
	
	private Usuario usuarioActual;
	
	private OfCourses() {
		repoUser = new UsuarioRepository(em);
		repoCurso = new CursoRepository(em);
		repoLeccion = new LeccionRepository(em);
		repoPregunta = new PreguntaRepository(em);
	}
	
	
	/** Devuelve la única instancia de controlador (patrón Singleton)
	 * 
	 *	@return Única instancia
	 */
	public static OfCourses getUnicaInstancia() {
		if(unicaInstancia == null) {
			unicaInstancia = new OfCourses();
		}
		return unicaInstancia;
	}
	
	public boolean Login(String user, String password) {
		
		if(user.contains("@")) {
			usuarioActual = repoUser.buscarPorEmail(user);
		}else {
			usuarioActual = repoUser.buscarPorNombre(user);
		}
		
		if(usuarioActual == null || !usuarioActual.getPassword().equals(password)) {
			usuarioActual = null;
			return false;
		}else{
			usuarioActual.comprobarRacha();
			return true;
		}
	}


	public boolean registerUser(String username, String password, String email, String url) {
		
		Usuario user = new Usuario(username, email, password, url);
		
		if(!repoUser.guardar(user)) {
			return false;
		}
		
		usuarioActual = user;
		return true;
	}
	
	public Image getFotoUsuarioActual() {
		
		Image imagen;
		
		try{
			imagen = new Image(usuarioActual.getFoto());
		}catch (Exception e) {
			imagen = new Image(getClass().getResource(FOTO_DEFECTO).toString());
		}
		
		return imagen;
	}


	public boolean addCurso(String file) {
		CargadorYAML cargador = new CargadorYAML();
		

		try{
			Curso curso = cargador.cargarCursoDesdeArchivo(file);
			usuarioActual.addCurso(curso);
			
			for(Leccion l: curso.getLecciones()) {
				for(Pregunta p: l.getPreguntas()) {
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


	public List<Curso> getCursosDisponibles() {
		return usuarioActual.getCursos();
	}


	public Curso getCurso(String cursoActual) {
		return usuarioActual.getCurso(cursoActual);
	}


	public Pregunta getSiguientePregunta(Leccion actual) {
		return actual.getSiguientePregunta();
	}


	public String getNombreUsuario() {
		return usuarioActual.getUsername();
	}
	
	public void recargarVidasSiCorresponde() {
	    boolean recarga = usuarioActual.recargarSiEsNuevoDia();
	    if(recarga) { repoUser.modificarUsuario(usuarioActual);}
	}
	
	public void perderVida() {
	    usuarioActual.perderVida();
	    repoUser.modificarUsuario(usuarioActual);
	}
	
	public boolean tieneVidas() {
	    return !usuarioActual.estaSinVidas();
	}
	
	public int getVidas() {
	    return usuarioActual.getVidas();
	}
	
	public void actualizarUsuario(String nuevoNombre, String nuevaContrasena, String nuevoCorreo, File nuevaFoto) {
		if (nuevoNombre != null) usuarioActual.setUsername(nuevoNombre);
	    if (nuevaContrasena != null) usuarioActual.setPassword(nuevaContrasena);
	    if (nuevoCorreo != null) usuarioActual.setEmail(nuevoCorreo);
	    if (nuevaFoto != null) usuarioActual.setFoto(nuevaFoto.getAbsolutePath());
	    
		repoUser.modificarUsuario(usuarioActual);
	}


	public String getCorreoUsuario() {
		return usuarioActual.getEmail();
	}


	public int getRacha() {
		return usuarioActual.getRacha();
	}


	public int getMaximaRacha() {
		return usuarioActual.getRachaMaxima();
	}

	public int preguntasContestadas() {
		return usuarioActual.obtenerPreguntasContestadas();
	}
}
