package modelo;

import java.util.ArrayList;
import java.util.List;

public class PreguntaOrdenar extends Pregunta {
    private List<String> opciones;

    public PreguntaOrdenar(String texto, String respuesta, List<String> opciones) {
        super(texto, respuesta);
        this.opciones = opciones;
    }
    
    @Override
    public boolean esCorrecta(String respuestaUsuario) {
        return respuesta.equals(respuestaUsuario);
    }
    
    //getters
    public List<String> getOpciones() {
		return new ArrayList<String>(opciones);
	}
}
