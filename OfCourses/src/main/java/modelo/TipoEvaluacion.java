package modelo;

import java.util.List;

public interface TipoEvaluacion {
	boolean evaluar(Pregunta pregunta, String respuestaUsuario);
    default boolean evaluar(Pregunta pregunta, List<String> respuestaUsuario) {
        throw new UnsupportedOperationException();
    }
}
