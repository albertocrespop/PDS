package servicios;
import java.util.List;

/**
 * Clase auxiliar usada para representar una lección dentro de un curso YAML.
 * Esta clase es utilizada por SnakeYAML para deserializar la estructura de una lección,
 * incluyendo su título, descripción y las preguntas que contiene.
 */
public class LeccionYAML {

    /** Título de la lección */
    private String titulo;

    /** Descripción de la lección */
    private String descripcion;

    /** Lista de preguntas asociadas a la lección */
    private List<PreguntaYAML> preguntas;

    /**
     * Obtiene el título de la lección.
     * @return el título como String
     */
    public String getTitulo() { return titulo; }

    /**
     * Establece el título de la lección.
     * @param titulo el nuevo título
     */
    public void setTitulo(String titulo) { this.titulo = titulo; }

    /**
     * Obtiene la descripción de la lección.
     * @return la descripción como String
     */
    public String getDescripcion() { return descripcion; }

    /**
     * Establece la descripción de la lección.
     * @param descripcion la nueva descripción
     */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /**
     * Obtiene la lista de preguntas de la lección.
     * @return lista de objetos PreguntaYAML
     */
    public List<PreguntaYAML> getPreguntas() { return preguntas; }

    /**
     * Establece la lista de preguntas para la lección.
     * @param preguntas nueva lista de preguntas
     */
    public void setPreguntas(List<PreguntaYAML> preguntas) { this.preguntas = preguntas; }
}