package modelo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

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

    public Pregunta() {}

    public Pregunta(String enunciado, String respuesta) {
        this.enunciado = enunciado;
        this.respuesta = respuesta;
    }

    public abstract boolean comprobarRespuesta(String respuestaUsuario);

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
