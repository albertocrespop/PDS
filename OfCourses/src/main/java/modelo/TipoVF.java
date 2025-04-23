package modelo;

public class TipoVF implements TipoEvaluacion {
    public boolean evaluar(Pregunta pregunta, String r) {
        return r != null && r.equalsIgnoreCase(pregunta.getRespuesta());
    }
}
