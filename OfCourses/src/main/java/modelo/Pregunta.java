package modelo;

public abstract class Pregunta {
    protected String texto;
    protected String respuesta;

    public Pregunta(String texto, String respuesta) {
        this.texto = texto;
        this.respuesta = respuesta;
    }
    
    public abstract boolean esCorrecta(String entradaUsuario);
    
    
    //gettes
    public String getRespuesta() {
		return respuesta;
	}
    
    public String getTexto() {
		return texto;
	}
}