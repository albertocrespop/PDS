package modelo;

public class EvaluacionVF implements EstrategiaEvaluacion {
    public boolean evaluar(Pregunta pregunta, String r) {
        return r != null && r.equalsIgnoreCase(pregunta.getRespuesta());
    }
}
