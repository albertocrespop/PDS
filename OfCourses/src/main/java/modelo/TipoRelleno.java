package modelo;

public class TipoRelleno implements TipoEvaluacion {
    public boolean evaluar(Pregunta pregunta, String r) {
        return r != null && r.trim().equalsIgnoreCase(pregunta.getRespuesta().trim());
    }
}