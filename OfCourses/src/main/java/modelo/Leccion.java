package modelo;

import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.*;

/**
 * Representa una lección dentro de un curso.
 * 
 * Una lección contiene una lista de preguntas, así como información
 * sobre su título, descripción, progreso y si ha sido completada.
 */
@Entity
@Table(name = "lecciones")
public class Leccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    @OneToMany
    @JoinColumn(name = "leccion_id")
    private List<Pregunta> preguntas;

    private int ultimaPregunta;

    private boolean completada;

    /**
     * Constructor sin parámetros requerido por Hibernate.
     */
    public Leccion() {}

    /**
     * Crea una nueva lección con título, descripción y lista de preguntas.
     *
     * @param titulo título de la lección
     * @param descripcion descripción de la lección
     * @param preguntas lista de preguntas asociadas
     */
    public Leccion(String titulo, String descripcion, List<Pregunta> preguntas) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.preguntas = new ArrayList<Pregunta>(preguntas);
        this.completada = false;
        this.ultimaPregunta = 0;
    }

    // Getters

    /**
     * Devuelve el identificador único de la lección.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Devuelve la descripción de la lección.
     *
     * @return descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Devuelve una copia de la lista de preguntas de la lección.
     *
     * @return lista de preguntas
     */
    public List<Pregunta> getPreguntas() {
        return new ArrayList<Pregunta>(preguntas);
    }

    /**
     * Devuelve el título de la lección.
     *
     * @return título
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Indica si la lección está marcada como completada.
     *
     * @return {@code true} si completada; {@code false} en caso contrario
     */
    public boolean getCompletada() {
        return completada;
    }

    /**
     * Devuelve el índice de la última pregunta respondida.
     *
     * @return índice de pregunta
     */
    public int getUltimaPregunta() {
        return ultimaPregunta;
    }

    /**
     * Devuelve la pregunta actual basada en el índice de última pregunta.
     *
     * @return pregunta actual
     */
    public Pregunta getPreguntaActual() {
        return preguntas.get(ultimaPregunta);
    }

    // Setters

    /**
     * Establece el estado de completitud de la lección.
     *
     * @param completada {@code true} si está completada; {@code false} en caso contrario
     */
    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    /**
     * Establece el índice de la última pregunta respondida.
     *
     * @param ultimaPregunta nuevo índice
     */
    public void setUltimaPregunta(int ultimaPregunta) {
        this.ultimaPregunta = ultimaPregunta;
    }

    /**
     * Establece la lista de preguntas de la lección.
     *
     * @param preguntas nueva lista de preguntas
     */
    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    // Métodos de clase

    /**
     * Aplica una estrategia de ordenación a la lista de preguntas.
     *
     * @param estrategia estrategia a aplicar
     */
    public void aplicarEstrategia(Estrategia estrategia) {
        setPreguntas(estrategia.ordenar(preguntas));
    }

    /**
     * Avanza a la siguiente pregunta de la lección.
     * 
     * Si no hay más preguntas, marca la lección como completada.
     *
     * @return siguiente pregunta o {@code null} si no hay más
     */
    public Pregunta getSiguientePregunta() {
        ultimaPregunta++;
        if (ultimaPregunta >= preguntas.size()) {
            completada = true;
            return null;
        }
        return preguntas.get(ultimaPregunta);
    }

    /**
     * Verifica si la lección ha sido completada (basado en el índice de preguntas).
     *
     * @return {@code true} si completada; {@code false} en caso contrario
     */
    public boolean isCompletada() {
        if (ultimaPregunta >= preguntas.size()) {
            return true;
        }
        return false;
    }
}
