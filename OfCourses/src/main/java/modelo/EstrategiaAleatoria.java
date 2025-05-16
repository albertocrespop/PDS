package modelo;

import java.util.Collections;
import java.util.List;

/**
 * Implementación de la estrategia que ordena las preguntas de forma aleatoria.
 * 
 * Utiliza {@code Collections.shuffle} para reordenar la lista original de preguntas.
 */
public class EstrategiaAleatoria extends Estrategia {

    /**
     * Constructor por defecto.
     */
    public EstrategiaAleatoria() {}

    /**
     * Reordena aleatoriamente la lista de preguntas recibida.
     *
     * @param preguntas lista original de preguntas
     * @return lista de preguntas desordenadas aleatoriamente
     */
    @Override
    public List<Pregunta> ordenar(List<Pregunta> preguntas) {
        Collections.shuffle(preguntas);
        return preguntas;
    }

    /**
     * Devuelve el nombre completo de la clase como cadena.
     *
     * @return nombre de clase
     */
    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
