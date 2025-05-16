package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Estrategia que duplica la lista de preguntas para fomentar la repetición.
 * 
 * Realiza dos mezclas aleatorias de las mismas preguntas y las concatena,
 * generando una lista con el doble de elementos para reforzar el aprendizaje.
 */
public class EstrategiaRepeticion extends Estrategia {

    /**
     * Constructor por defecto.
     */
    public EstrategiaRepeticion() {}

    /**
     * Devuelve una lista con las preguntas duplicadas y mezcladas.
     * 
     * La lista original se mezcla aleatoriamente dos veces y se añade
     * a la nueva lista de resultados.
     *
     * @param preguntas lista original de preguntas
     * @return lista duplicada y desordenada
     */
    @Override
    public List<Pregunta> ordenar(List<Pregunta> preguntas) {
        List<Pregunta> result = new ArrayList<Pregunta>();
        Collections.shuffle(preguntas);
        result.addAll(preguntas);
        Collections.shuffle(preguntas);
        result.addAll(preguntas);
        return result;
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
