package modelo;

public class PreguntaFlashCard extends Pregunta {
    public PreguntaFlashCard(String texto, String respuesta) {
        super(texto, respuesta);
    }
    
    @Override
    public boolean esCorrecta(String entradaUsuario) {
        return true; // Siempre correcta (estudio pasivo)
    }
}