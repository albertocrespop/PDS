package modelo;

import java.util.List;

public class EstrategiaSecuencial extends Estrategia {
    public EstrategiaSecuencial() {}

    @Override
    public List<Pregunta> ordenar(List<Pregunta> preguntas) {
        return preguntas;
    }
}
