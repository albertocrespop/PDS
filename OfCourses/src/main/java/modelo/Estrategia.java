package modelo;

import java.util.List;

/**
 * Clase abstracta que define una estrategia de ordenación de preguntas.
 * 
 * Las subclases deben implementar el método {@code ordenar} para establecer
 * un criterio específico de organización de las preguntas en una lección o curso.
 */
public abstract class Estrategia {

    /**
     * Ordena una lista de preguntas según la estrategia definida.
     *
     * @param preguntas lista original de preguntas
     * @return nueva lista de preguntas ordenadas
     */
    public abstract List<Pregunta> ordenar(List<Pregunta> preguntas);
}
