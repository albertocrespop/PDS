package modelo;

import java.util.List;

public class Pregunta {
    private String texto;
    private String respuesta;
    private TipoEvaluacion tipo;

    public Pregunta(String texto, String respuesta, TipoEvaluacion estrategia) {
        this.texto = texto;
        this.respuesta = respuesta;
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
}
