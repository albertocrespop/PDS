package modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("rellenar")
public class PreguntaRellenarPalabras extends Pregunta {
	public static final String CARACTER_HUECO = "???";
	
	
    public PreguntaRellenarPalabras() {
        super(); // Constructor vacío necesario
    }

    public PreguntaRellenarPalabras(String respuesta, String texto) {
        super(texto, respuesta);
    }

    @Override
    public boolean comprobarRespuesta(String respuestaUsuario) {
        return getRespuesta().equals(respuestaUsuario);
    }
}
