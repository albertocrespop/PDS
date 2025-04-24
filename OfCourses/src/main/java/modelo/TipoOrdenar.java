package modelo;

public class TipoOrdenar implements TipoEvaluacion {
	@Override
    public boolean evaluar(Pregunta pregunta, String respuestaUsuario) {
        return pregunta.getRespuesta().equals(respuestaUsuario);
    }
}