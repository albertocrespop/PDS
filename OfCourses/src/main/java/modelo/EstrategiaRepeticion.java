package modelo;

import java.util.ArrayList;
import java.util.List;

public class EstrategiaRepeticion extends Estrategia {
    public EstrategiaRepeticion() {}

    @Override
    public List<Pregunta> ordenar(List<Pregunta> preguntas) {
        List<Pregunta> result = new ArrayList<Pregunta>();
        for (int i = 0; i < preguntas.size(); i++) {
            result.add(preguntas.get(i));
            if (i % 3 == 2) result.add(preguntas.get(i));
        }
        return result;
    }
    
    @Override
    public String toString() {
    	return this.getClass().getName();
    }
}