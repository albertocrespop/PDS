package modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Representa una pregunta del tipo FlashCard.
 * 
 * Este tipo de pregunta no se evalúa automáticamente, por lo que siempre se considera correcta.
 * Se usa principalmente para memorización o revisión de conceptos.
 */
@Entity
@DiscriminatorValue("flashcard")
public class PreguntaFlashCard extends Pregunta {

    /**
     * Constructor sin parámetros requerido por JPA.
     */
    public PreguntaFlashCard() {
        super();
    }

    /**
     * Constructor que inicializa una FlashCard con enunciado y respuesta.
     *
     * @param enunciado enunciado de la pregunta
     * @param respuesta respuesta de la tarjeta
     */
    public PreguntaFlashCard(String enunciado, String respuesta) {
        super(enunciado, respuesta);
    }

    /**
     * Método que siempre devuelve {@code true}, ya que las FlashCards no se evalúan.
     *
     * @param respuestaUsuario respuesta proporcionada por el usuario (ignorada)
     * @return {@code true}
     */
    @Override
    public boolean comprobarRespuesta(String respuestaUsuario) {
        return true; // Flashcards no se evalúan
    }
}
