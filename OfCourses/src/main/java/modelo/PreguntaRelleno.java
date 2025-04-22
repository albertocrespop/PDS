package modelo;

public class PreguntaRelleno extends Pregunta {
    public PreguntaRelleno(String texto, String respuesta) {
        super(texto, respuesta);
    }

    @Override
    public boolean esCorrecta(String entradaUsuario) {
        return entradaUsuario != null && entradaUsuario.trim().equalsIgnoreCase(respuesta.trim());
    }
}
