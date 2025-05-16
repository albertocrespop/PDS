package modelo;



import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

import jakarta.persistence.*;

/**
 * RepresFenta un usuario registrado en la plataforma.
 * 
 * Contiene información personal, progreso en los cursos,
 * estadísticas de actividad y control de vidas.
 */
@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario {

    private static final int MAX_VIDAS = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int vidas;

    private LocalDate ultimaRecarga;

    @OneToMany(mappedBy = "autor")
    private List<Curso> cursos;

    private String foto;

    private LocalDate ultimoDia;
    private int racha;
    private int rachaMaxima;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Usuario() {}

    /**
     * Crea un nuevo usuario con los datos iniciales.
     * 
     * @param username nombre de usuario
     * @param email correo electrónico
     * @param password contraseña
     * @param foto ruta o nombre del archivo de la foto del usuario
     */
    public Usuario(String username, String email, String password, String foto) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.foto = foto;
        this.cursos = new ArrayList<Curso>();
        this.vidas = MAX_VIDAS;
        this.setUltimaRecarga(LocalDate.now());
        this.racha = 0;
        this.rachaMaxima = 0;
        this.ultimoDia = LocalDate.now();
    }

    // Getters

    /**
     * Devuelve una copia de la lista de cursos del usuario.
     * 
     * @return lista de cursos
     */
    public List<Curso> getCursos() {
        return new ArrayList<Curso>(cursos);
    }

    /**
     * Devuelve el correo electrónico del usuario.
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Devuelve el número total de horas acumuladas en todos los cursos.
     * 
     * @return suma de horas
     */
    public int getHorasTotales() {
        return cursos.stream()
                .collect(Collectors.summingInt(Curso::getHoras));
    }

    /**
     * Devuelve la contraseña del usuario.
     * 
     * @return contraseña
     */
    public String getPassword() {
        return password;
    }

    /**
     * Devuelve la cantidad de vidas actuales del usuario.
     * 
     * @return número de vidas
     */
    public int getVidas() {
        return vidas;
    }

    /**
     * Devuelve la racha actual de días consecutivos activos del usuario.
     * 
     * @return racha actual
     */
    public int getRacha() {
        return racha;
    }

    /**
     * Devuelve la mayor racha alcanzada por el usuario.
     * 
     * @return racha máxima
     */
    public int getRachaMaxima() {
        return rachaMaxima;
    }

    /**
     * Devuelve el nombre de usuario.
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Devuelve el identificador único del usuario.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Devuelve la última fecha en la que el usuario estuvo activo.
     * 
     * @return último día activo
     */
    public LocalDate getUltimoDia() {
        return ultimoDia;
    }

    /**
     * Devuelve la foto del usuario.
     * 
     * @return foto
     */
    public String getFoto() {
        return foto;
    }

    // Setters

    /**
     * Establece la lista de cursos del usuario.
     * 
     * @param cursos nueva lista de cursos
     */
    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    /**
     * Establece el correo electrónico del usuario.
     * 
     * @param email nuevo email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Establece la contraseña del usuario.
     * 
     * @param password nueva contraseña
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Establece la racha actual del usuario.
     * 
     * @param racha nueva racha
     */
    public void setRacha(int racha) {
        this.racha = racha;
    }

    /**
     * Establece la racha máxima del usuario.
     * 
     * @param rachaMaxima nueva racha máxima
     */
    public void setRachaMaxima(int rachaMaxima) {
        this.rachaMaxima = rachaMaxima;
    }

    /**
     * Establece el nombre de usuario.
     * 
     * @param username nuevo nombre de usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Establece la fecha de la última recarga de vidas.
     * 
     * @param ultimaRecarga nueva fecha de recarga
     */
    public void setUltimaRecarga(LocalDate ultimaRecarga) {
        this.ultimaRecarga = ultimaRecarga;
    }

    /**
     * Establece el último día de actividad del usuario.
     * 
     * @param ultimoDia nueva fecha
     */
    public void setUltimoDia(LocalDate ultimoDia) {
        this.ultimoDia = ultimoDia;
    }

    /**
     * Añade un nuevo curso a la lista de cursos del usuario.
     * 
     * @param c curso a añadir
     */
    public void addCurso(Curso c) {
        cursos.add(c);
    }

    /**
     * Recarga las vidas del usuario si ha pasado al menos un día desde la última recarga.
     * 
     * @return {@code true} si se recargaron las vidas; {@code false} en caso contrario.
     */
    public boolean recargarSiEsNuevoDia() {
        LocalDate hoy = LocalDate.now();
        if (!ultimaRecarga.equals(hoy)) {
            this.vidas = MAX_VIDAS;
            this.ultimaRecarga = hoy;
            return true;
        }
        return false;
    }

    /**
     * Resta una vida al usuario si todavía le quedan vidas disponibles.
     */
    public void perderVida() {
        if (vidas > 0) {
            vidas--;
        }
    }

    /**
     * Indica si el usuario se ha quedado sin vidas.
     * 
     * @return {@code true} si no le quedan vidas; {@code false} en caso contrario.
     */
    public boolean estaSinVidas() {
        return vidas == 0;
    }

    /**
     * Establece la foto del usuario.
     * 
     * @param foto nueva foto
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * Devuelve una representación textual del objeto usuario.
     * 
     * @return cadena con id, nombre y email
     */
    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + username + "', email='" + email + "'}";
    }

    /**
     * Devuelve el curso cuyo título coincide con el nombre proporcionado.
     * 
     * @param cursoActual título del curso
     * @return curso correspondiente o {@code null} si no se encuentra
     */
    public Curso getCurso(String cursoActual) {
        for (Curso c : cursos) {
            if (c.getTitulo().equals(cursoActual)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Comprueba si ha pasado un nuevo día y actualiza la racha si corresponde.
     */
    public void comprobarRacha() {
        if (ultimoDia.equals(LocalDate.now())) {
            return;
        }
        ultimoDia = LocalDate.now();
        racha = racha + 1;
        if (racha > rachaMaxima) {
            rachaMaxima = racha;
        }
    }

    /**
     * Devuelve el número total de preguntas respondidas en todos los cursos.
     * 
     * @return número total de preguntas respondidas
     */
    public int obtenerPreguntasContestadas() {
        int resultado = 0;
        for (Curso c : cursos) {
            resultado = resultado + c.getPreguntasContestadas();
        }
        return resultado;
    }
}