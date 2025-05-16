package modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Representa una pregunta en la que el usuario debe rellenar una palabra faltante.
 * 
 * El enunciado contiene un hueco representado por el carácter especial {@code ???},
 * y la respuesta correcta es la palabra que debe completar el texto.
 */
@Entity
@DiscriminatorValue("rellenar")
public class PreguntaRellenarPalabras extends Pregunta {

    /**
     * Carácter que representa el hueco que debe ser rellenado en el enunciado.
     */
    public static final String CARACTER_HUECO = "???";

    /**
     * Constructor sin parámetros requerido por JPA.
     */
    public PreguntaRellenarPalabras() {
        super(); // Constructor vacío necesario
    }

    /**
     * Constructor que inicializa la pregunta con el texto (enunciado con hueco)
     * y la respuesta correcta que debe ir en el hueco.
     *
     * @param respuesta palabra que completa el hueco
     * @param texto enunciado con el hueco representado por {@code ???}
     */
    public PreguntaRellenarPalabras(String respuesta, String texto) {
        super(texto, respuesta);
    }

    /**
     * Compara la respuesta del usuario con la respuesta correcta.
     *
     * @param respuestaUsuario palabra ingresada por el usuario
     * @return {@code true} si coincide con la respuesta esperada; {@code false} en caso contrario
     */
    @Override
    public boolean comprobarRespuesta(String respuestaUsuario) {
        return getRespuesta().equals(respuestaUsuario);
    }
}
