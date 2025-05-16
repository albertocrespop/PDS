package modelo;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Representa una pregunta en la que el usuario debe ordenar palabras correctamente.
 * 
 * Las opciones se almacenan como una lista de palabras desordenadas. La evaluación se basa
 * en comparar la respuesta del usuario con la respuesta correcta almacenada como cadena.
 */
@Entity
@DiscriminatorValue("ordenar")
public class PreguntaOrdenarPalabras extends Pregunta {

    @ElementCollection
    private List<String> opciones = new ArrayList<>();

    /**
     * Constructor sin parámetros requerido por JPA.
     */
    public PreguntaOrdenarPalabras() {
        super(); // Requerido por JPA
    }

    /**
     * Constructor que inicializa la pregunta con enunciado y respuesta correcta.
     *
     * @param enunciado enunciado de la pregunta
     * @param respuesta cadena que representa la respuesta correcta
     */
    public PreguntaOrdenarPalabras(String enunciado, String respuesta) {
        super(enunciado, respuesta);
    }

    /**
     * Devuelve una copia defensiva de las opciones de ordenación.
     *
     * @return lista de palabras desordenadas
     */
    public List<String> getOpciones() {
        return new ArrayList<>(opciones);
    }

    /**
     * Establece la lista de opciones de ordenación.
     *
     * @param opciones nueva lista de palabras
     */
    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    /**
     * Compara la respuesta del usuario con la respuesta correcta almacenada.
     *
     * @param respuestaUsuario cadena ingresada por el usuario
     * @return {@code true} si la respuesta coincide exactamente; {@code false} en caso contrario
     */
    @Override
    public boolean comprobarRespuesta(String respuestaUsuario) {
        return getRespuesta().equals(respuestaUsuario);
    }
}
