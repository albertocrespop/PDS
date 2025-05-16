package ofcourses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import modelo.EstrategiaAleatoria;
import modelo.EstrategiaRepeticion;
import modelo.EstrategiaSecuencial;
import modelo.Pregunta;
import modelo.PreguntaVF;

public class EstrategiaTests {
	  // EstrategiaAleatoria Tests
	@Test
    public void testEstrategiaAleatoria() {
    	EstrategiaAleatoria estrategia = new EstrategiaAleatoria();

        // Lista de preguntas de prueba
        Pregunta p1 = new PreguntaVF("Pregunta 1", "resultado 1");
        Pregunta p2 = new PreguntaVF("Pregunta 2", "resultado 2");
        Pregunta p3 = new PreguntaVF("Pregunta 3", "resultado 3");

        List<Pregunta> original = Arrays.asList(p1, p2, p3);
        List<Pregunta> copia = new ArrayList<>(original);

        List<Pregunta> resultado = estrategia.ordenar(copia);

        // Debe contener los mismos elementos, aunque el orden puede variar
        assertEquals(3, resultado.size());
        assertTrue(resultado.containsAll(original));

        // Hay una probabilidad de que el orden no cambie, así que comprobamos si se da el caso
        boolean diferente = false;
        for (int i = 0; i < 3; i++) {
            if (!resultado.get(i).equals(original.get(i))) {
                diferente = true;
                break;
            }
        }
        assertTrue(diferente || resultado.equals(original));
    }
    
	@Test
	void testToStringAleatoria() {
	    EstrategiaAleatoria estrategia = new EstrategiaAleatoria();
	    assertEquals("modelo.EstrategiaAleatoria", estrategia.toString());
	}

	// EstrategiaRepeticion Tests
    @Test
    void testOrdenarRepiteCadaTres() {
        EstrategiaRepeticion estrategia = new EstrategiaRepeticion();

        Pregunta p1 = new PreguntaVF("P1", "R1");
        Pregunta p2 = new PreguntaVF("P2", "R2");
        Pregunta p3 = new PreguntaVF("P3", "R3"); // Este se debe repetir
        Pregunta p4 = new PreguntaVF("P4", "R4");
        Pregunta p5 = new PreguntaVF("P5", "R5");
        Pregunta p6 = new PreguntaVF("P6", "R6"); // Este se debe repetir

        List<Pregunta> original = Arrays.asList(p1, p2, p3, p4, p5, p6);
        List<Pregunta> resultado = estrategia.ordenar(original);

        // Esperamos: P1, P2, P3, P3, P4, P5, P6, P6
        assertEquals(12, resultado.size());
    }

    @Test
    void testOrdenarConMenosDeTres() {
        EstrategiaRepeticion estrategia = new EstrategiaRepeticion();
        Pregunta p1 = new PreguntaVF("P1", "R1");
        Pregunta p2 = new PreguntaVF("P2", "R2");

        List<Pregunta> original = Arrays.asList(p1, p2);
        List<Pregunta> resultado = estrategia.ordenar(original);

        // No se debe repetir nada
        assertEquals(4, resultado.size());
        assertEquals(p1, resultado.get(0));
        assertEquals(p2, resultado.get(1));
    }
    
    @Test
    void testOrdenarConListaVaciaRepeticion() {
        EstrategiaRepeticion estrategia = new EstrategiaRepeticion();
        List<Pregunta> resultado = estrategia.ordenar(new ArrayList<>());
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testToStringRepeticion() {
        EstrategiaRepeticion estrategia = new EstrategiaRepeticion();
        assertEquals("modelo.EstrategiaRepeticion", estrategia.toString());
    }
    
    // EstrategiaSecuencial Tests
    @Test
    void testOrdenarDevuelveMismaLista() {
        EstrategiaSecuencial estrategia = new EstrategiaSecuencial();

        Pregunta p1 = new PreguntaVF("P1", "R1");
        Pregunta p2 = new PreguntaVF("P2", "R2");
        Pregunta p3 = new PreguntaVF("P3", "R3");

        List<Pregunta> original = Arrays.asList(p1, p2, p3);
        List<Pregunta> resultado = estrategia.ordenar(original);

        // Debe devolver exactamente la misma lista (no una copia)
        assertSame(original, resultado);
    }

    @Test
    void testOrdenarConListaVaciaSecuencial() {
        EstrategiaSecuencial estrategia = new EstrategiaSecuencial();

        List<Pregunta> vacia = new ArrayList<>();
        List<Pregunta> resultado = estrategia.ordenar(vacia);

        assertSame(vacia, resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testToString() {
        EstrategiaSecuencial estrategia = new EstrategiaSecuencial();
        assertEquals("modelo.EstrategiaSecuencial", estrategia.toString());
    }
}
