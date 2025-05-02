package modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("rellenar")
public class PreguntaRellenarPalabras extends Pregunta {

    private String texto;

    public PreguntaRellenarPalabras() {
        super(); // Constructor vacío necesario
    }

    public PreguntaRellenarPalabras(String enunciado, String respuesta, String texto) {
        super(enunciado, respuesta);
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public boolean comprobarRespuesta(String respuestaUsuario) {
        return getRespuesta().equals(respuestaUsuario);
    }
}
