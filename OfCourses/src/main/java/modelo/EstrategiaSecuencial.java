package modelo;

import java.util.List;

/**
 * Estrategia que mantiene el orden original de las preguntas.
 * 
 * No se realiza ningún tipo de modificación en la lista recibida.
 */
public class EstrategiaSecuencial extends Estrategia {

    /**
     * Constructor por defecto.
     */
    public EstrategiaSecuencial() {}

    /**
     * Devuelve la lista original de preguntas sin alteraciones.
     *
     * @param preguntas lista original de preguntas
     * @return misma lista sin cambios
     */
    @Override
    public List<Pregunta> ordenar(List<Pregunta> preguntas) {
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
