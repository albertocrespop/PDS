package modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@DiscriminatorValue("ordenar")
public class PreguntaOrdenarPalabras extends Pregunta {

    @ElementCollection
    private List<String> opciones = new ArrayList<>();

    public PreguntaOrdenarPalabras() {
        super(); // Requerido por JPA
    }

    public PreguntaOrdenarPalabras(String enunciado, String respuesta) {
        super(enunciado, respuesta);
    }

    public List<String> getOpciones() {
        return new ArrayList<>(opciones); // protección defensiva
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    @Override
    public boolean comprobarRespuesta(String respuestaUsuario) {
        return getRespuesta().equals(respuestaUsuario);
    }
}
