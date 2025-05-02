package modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("flashcard")
public class PreguntaFlashCard extends Pregunta {

    public PreguntaFlashCard() {
        super();  // Requerido por JPA
    }

    public PreguntaFlashCard(String enunciado, String respuesta) {
        super(enunciado, respuesta);
    }

    @Override
    public boolean comprobarRespuesta(String respuestaUsuario) {
        return true; // Flashcards no se evalúan
    }
}

