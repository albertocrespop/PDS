package modelo;

public class EvaluacionRelleno implements EstrategiaEvaluacion {
    public boolean evaluar(Pregunta pregunta, String r) {
        return r != null && r.trim().equalsIgnoreCase(pregunta.getRespuesta().trim());
    }
}