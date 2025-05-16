package modelo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

/**
 * Clase abstracta que representa una pregunta genérica.
 * 
 * Esta clase es la base para distintos tipos de preguntas como FlashCard,
 * Verdadero/Falso, rellenar palabras y ordenar palabras. Utiliza herencia
 * JPA con una única tabla y discriminación por tipo.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pregunta", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PreguntaFlashCard.class, name = "flashcard"),
    @JsonSubTypes.Type(value = PreguntaVF.class, name = "vf"),
    @JsonSubTypes.Type(value = PreguntaRellenarPalabras.class, name = "relleno"),
    @JsonSubTypes.Type(value = PreguntaOrdenarPalabras.class, name = "ordenar")
})
public abstract class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String enunciado;
    private String respuesta;

    /**
     * Constructor sin parámetros requerido por JPA.
     */
    public Pregunta() {}

    /**
     * Constructor general para una pregunta.
     *
     * @param enunciado enunciado de la pregunta
     * @param respuesta respuesta correcta
     */
    public Pregunta(String enunciado, String respuesta) {
        this.enunciado = enunciado;
        this.respuesta = respuesta;
    }

    /**
     * Método abstracto que debe implementar cada subclase para comprobar
     * si la respuesta del usuario es correcta.
     *
     * @param respuestaUsuario respuesta proporcionada por el usuario
     * @return {@code true} si es correcta; {@code false} en caso contrario
     */
    public abstract boolean comprobarRespuesta(String respuestaUsuario);

    // Getters y setters

    /**
     * Devuelve el identificador de la pregunta.
     *
     * @return id de la pregunta
     */
    public Long getId() {
        return id;
    }

    /**
     * Devuelve el enunciado de la pregunta.
     *
     * @return enunciado
     */
    public String getEnunciado() {
        return enunciado;
    }

    /**
     * Establece el enunciado de la pregunta.
     *
     * @param enunciado nuevo enunciado
     */
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    /**
     * Devuelve la respuesta correcta de la pregunta.
     *
     * @return respuesta correcta
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * Establece la respuesta correcta de la pregunta.
     *
     * @param respuesta nueva respuesta
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
