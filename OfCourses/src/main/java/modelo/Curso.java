package modelo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Representa un curso compuesto por una lista de lecciones y asociado a un usuario autor.
 * 
 * Incluye información como título, descripción, número de horas, progreso,
 * y una estrategia de aprendizaje que puede aplicarse a sus lecciones.
 */
@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;
    private String descripcion;

    @OneToMany
    @JoinColumn(name = "curso_id")
    private List<Leccion> lecciones;

    private int leccionActual;

    private int horas;

    @Transient
    private Estrategia estrategia;

    @JsonProperty("estrategia")
    private String estrategiaString;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    /**
     * Constructor sin parámetros requerido por Hibernate.
     */
    public Curso() {}

    /**
     * Constructor principal usado para crear un curso desde datos JSON.
     *
     * @param titulo título del curso
     * @param descripcion descripción del curso
     * @param lecciones lista de lecciones del curso
     * @param estrategia nombre de la estrategia de aprendizaje asociada
     */
    public Curso(@JsonProperty("titulo") String titulo,
                 @JsonProperty("descripcion") String descripcion,
                 @JsonProperty("lecciones") List<Leccion> lecciones,
                 @JsonProperty("estrategia") String estrategia) {

        this.estrategiaString = estrategia;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.lecciones = new ArrayList<Leccion>(lecciones);
        this.horas = 0;
        this.leccionActual = 0;
    }

    // Getters

    /**
     * Devuelve el usuario autor del curso.
     *
     * @return autor del curso
     */
    public Usuario getAutor() {
        return autor;
    }

    /**
     * Devuelve la descripción del curso.
     *
     * @return descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Devuelve la estrategia de aprendizaje asociada al curso.
     *
     * @return estrategia
     */
    @JsonIgnore
    public Estrategia getEstrategia() {
        return estrategia;
    }

    /**
     * Devuelve la cantidad de horas dedicadas al curso.
     *
     * @return horas
     */
    public int getHoras() {
        return horas;
    }

    /**
     * Devuelve una copia de la lista de lecciones del curso.
     *
     * @return lista de lecciones
     */
    public List<Leccion> getLecciones() {
        return new ArrayList<Leccion>(lecciones);
    }

    /**
     * Devuelve el título del curso.
     *
     * @return título
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Devuelve el identificador único del curso.
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Devuelve el nombre de la estrategia como cadena.
     *
     * @return nombre de la estrategia
     */
    public String getEstrategiaString() {
        return estrategiaString;
    }

    /**
     * Devuelve el índice de la lección actual.
     *
     * @return lección actual
     */
    public int getLeccionActual() {
        return leccionActual;
    }

    // Setters

    /**
     * Establece la estrategia de aprendizaje del curso.
     *
     * @param estrategia objeto estrategia
     */
    @JsonIgnore
    public void setEstrategia(Estrategia estrategia) {
        this.estrategia = estrategia;
    }

    /**
     * Establece el autor del curso.
     *
     * @param autor usuario autor
     */
    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    /**
     * Establece las horas acumuladas del curso.
     *
     * @param horas número de horas
     */
    public void setHoras(int horas) {
        this.horas = horas;
    }

    /**
     * Establece la estrategia del curso como texto.
     *
     * @param estrategiaString nombre de la estrategia
     */
    public void setEstrategiaString(String estrategiaString) {
        this.estrategiaString = estrategiaString;
    }

    /**
     * Establece la lección actual del curso.
     *
     * @param leccionActual índice de lección
     */
    public void setLeccionActual(int leccionActual) {
        this.leccionActual = leccionActual;
    }

    /**
     * Establece la lista de lecciones del curso.
     *
     * @param lecciones lista de lecciones
     */
    public void setLecciones(List<Leccion> lecciones) {
        this.lecciones = lecciones;
    }

    // Métodos auxiliares

    /**
     * Aplica la estrategia correspondiente a cada lección del curso.
     * 
     * La estrategia se carga dinámicamente a partir del nombre indicado
     * en {@code estrategiaString}.
     */
    public void aplicarEstrategia() {
        String estrategiatexto = estrategiaString.substring(0, 1).toUpperCase() + estrategiaString.substring(1);
        estrategiatexto = "Estrategia" + estrategiatexto;
        try {
            this.estrategia = (Estrategia) Class.forName("modelo." + estrategiatexto)
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException
                | ClassNotFoundException e) {
            // Silenciado intencionalmente
        }
        lecciones.stream().forEach(l -> l.aplicarEstrategia(this.estrategia));
    }

    /**
     * Calcula el porcentaje de progreso del curso basado en lecciones completadas.
     *
     * @return progreso en porcentaje (0.0 a 100.0)
     */
    public double obtenerProgreso() {
        List<Leccion> completados = lecciones.stream()
                .filter(l -> l.getCompletada())
                .collect(Collectors.toList());

        return ((double) completados.size() / (double) lecciones.size()) * 100;
    }

    /**
     * Devuelve el número total de preguntas contestadas en todas las lecciones.
     *
     * @return número de preguntas contestadas
     */
    public int getPreguntasContestadas() {
        int resultado = 0;
        for (Leccion l : lecciones) {
            resultado = resultado + l.getUltimaPregunta();
        }
        return resultado;
    }
}