package modelo;

import java.util.Collections;
import java.util.List;

public class EstrategiaAleatoria extends Estrategia {
    public EstrategiaAleatoria() {}

    @Override
    public List<Pregunta> ordenar(List<Pregunta> preguntas) {
        Collections.shuffle(preguntas);
        return preguntas;
    }
}
