package modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Representa una pregunta de tipo Verdadero/Falso.
 * 
 * La evaluación de la respuesta se realiza ignorando mayúsculas y minúsculas.
 */
@Entity
@DiscriminatorValue("vf")
public class PreguntaVF extends Pregunta {

    /**
     * Constructor sin argumentos requerido por JPA.
     */
    public PreguntaVF() {
        super(); // Constructor sin argumentos requerido por JPA
    }

    /**
     * Constructor que inicializa la pregunta con su enunciado y respuesta.
     *
     * @param enunciado enunciado de la pregunta
     * @param respuesta respuesta correcta ("verdadero" o "falso")
     */
    public PreguntaVF(String enunciado, String respuesta) {
        super(enunciado, respuesta);
    }

    /**
     * Compara la respuesta del usuario con la respuesta correcta ignorando mayúsculas y minúsculas.
     *
     * @param respuestaUsuario respuesta proporcionada por el usuario
     * @return {@code true} si la respuesta coincide; {@code false} en caso contrario
     */
    @Override
    public boolean comprobarRespuesta(String respuestaUsuario) {
        return getRespuesta().equalsIgnoreCase(respuestaUsuario);
    }
}
