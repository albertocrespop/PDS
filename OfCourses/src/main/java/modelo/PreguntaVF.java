package modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("vf")
public class PreguntaVF extends Pregunta {

    public PreguntaVF() {
        super(); // Constructor sin argumentos requerido por JPA
    }

    public PreguntaVF(String enunciado, String respuesta) {
        super(enunciado, respuesta);
    }

    @Override
    public boolean comprobarRespuesta(String respuestaUsuario) {
        return getRespuesta().equalsIgnoreCase(respuestaUsuario);
    }
}
