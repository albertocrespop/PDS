package servicios;

/**
 * Clase auxiliar usada para representar una pregunta dentro de una lección YAML.
 * Contiene el texto de la pregunta, la respuesta esperada y el tipo de evaluación.
 * Es utilizada por SnakeYAML para deserializar preguntas desde un archivo YAML.
 */
public class PreguntaYAML {

    /** Texto de la pregunta */
    private String texto;

    /** Respuesta correcta a la pregunta */
    private String respuesta;

    /** Tipo de la pregunta (flashcard, relleno, vf, ordenar, etc.) */
    private String tipo;

    /**
     * Obtiene el texto de la pregunta.
     * @return el texto como String
     */
    public String getTexto() { return texto; }

    /**
     * Establece el texto de la pregunta.
     * @param texto el nuevo texto
     */
    public void setTexto(String texto) { this.texto = texto; }

    /**
     * Obtiene la respuesta de la pregunta.
     * @return la respuesta como String
     */
    public String getRespuesta() { return respuesta; }

    /**
     * Establece la respuesta de la pregunta.
     * @param respuesta la nueva respuesta
     */
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    /**
     * Obtiene el tipo de evaluación de la pregunta.
     * @return el tipo como String
     */
    public String getTipo() { return tipo; }

    /**
     * Establece el tipo de evaluación de la pregunta.
     * @param tipo el tipo (por ejemplo: "flashcard", "vf", etc.)
     */
    public void setTipo(String tipo) { this.tipo = tipo; }
}