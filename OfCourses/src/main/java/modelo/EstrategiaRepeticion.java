package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EstrategiaRepeticion extends Estrategia {
    public EstrategiaRepeticion() {}

    @Override
    public List<Pregunta> ordenar(List<Pregunta> preguntas) {
        List<Pregunta> result = new ArrayList<Pregunta>();
        Collections.shuffle(preguntas);
        result.addAll(preguntas);
        Collections.shuffle(preguntas);
        result.addAll(preguntas);
        return result;
    }
    
    @Override
    public String toString() {
    	return this.getClass().getName();
    }
}