package modelo;

import java.util.List;

public class TipoOrdenar implements TipoEvaluacion {
    @Override
    public boolean evaluar(Pregunta pregunta, String respuestaUsuario) {
        throw new UnsupportedOperationException("Esta estrategia requiere una lista de respuestas");
    }

    @Override
    public boolean evaluar(Pregunta pregunta, List<String> r) {
        return pregunta.getOpciones().equals(r);
    }
}