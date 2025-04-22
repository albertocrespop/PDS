package modelo;

import java.util.ArrayList;
import java.util.List;

public class Pregunta {
    private String texto;
    private String respuesta;
    private List<String> opciones;
    private EstrategiaEvaluacion tipo;

    public Pregunta(String texto, String respuesta, List<String> opciones, EstrategiaEvaluacion estrategia) {
        this.texto = texto;
        this.respuesta = respuesta;
        this.opciones = new ArrayList<String>(opciones);
        this.tipo = estrategia;
    }

    public boolean esCorrecta(String respuestaUsuario) {
        return tipo.evaluar(this, respuestaUsuario);
    }

   
    //Getters
    public String getTexto() { 
    	return texto; 
    }
    
    public String getRespuesta() { 
    	return respuesta; 
    }
    
    public List<String> getOpciones() {
    	return new ArrayList<String>(opciones); 
    }
}
