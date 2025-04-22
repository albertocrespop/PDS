package modelo;

import java.util.ArrayList;
import java.util.List;

public class PreguntaVF extends Pregunta {
    private List<String> opciones;

    public PreguntaVF(String texto, String respuesta, List<String> opciones) {
        super(texto, respuesta);
        this.opciones = opciones;
    }
    
    @Override
    public boolean esCorrecta(String entradaUsuario) {
        return entradaUsuario != null && entradaUsuario.equalsIgnoreCase(respuesta);
    }
    
    //getters
    public List<String> getOpciones() {
		return new ArrayList<String>(opciones);
	}
}
