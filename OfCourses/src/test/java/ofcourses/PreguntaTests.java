package ofcourses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import modelo.Pregunta;
import modelo.PreguntaFlashCard;
import modelo.PreguntaOrdenarPalabras;
import modelo.PreguntaRellenarPalabras;
import modelo.PreguntaVF;

public class PreguntaTests {
	// PreguntaVF Tests
	@Test
    void testGetSetPregunta() {
		Pregunta p = new PreguntaVF("P1", "R1");
		p.setEnunciado("Enunciado de prueba");
		assertEquals("Enunciado de prueba", p.getEnunciado());
		p.setRespuesta("Respuesta 1");
		assertEquals("Respuesta 1", p.getRespuesta());
		assertNull(p.getId());
	}
	
	@Test
	void testComprobarRespuestaVF() {
		Pregunta p = new PreguntaVF("P1", "Respuesta del usuario");
		assertTrue(p.comprobarRespuesta("Respuesta del usuario"));
	}
	
	// PreguntaFlashCard Tests
	@Test
	void testComprobarRespuestaFlashCard() {
		Pregunta p = new PreguntaFlashCard("P1", "Respuesta del usuario");
		assertTrue(p.comprobarRespuesta("Respuesta del usuario"));
	}
	
	// PreguntaOrdenarPalabras Tests
	@Test
    void testConstructorParametrizado() {
        String enunciado = "Ordena las palabras";
        String respuesta = "Hola mundo";
        String opcionesTexto = "mundo Hola";

        PreguntaOrdenarPalabras pregunta = new PreguntaOrdenarPalabras(enunciado, respuesta, opcionesTexto);

        assertEquals(enunciado, pregunta.getEnunciado());
        assertEquals(respuesta, pregunta.getRespuesta());

        List<String> opcionesEsperadas = Arrays.asList("mundo", "Hola");
        assertEquals(opcionesEsperadas, pregunta.getOpciones());
    }

    @Test
    void testSetYGetOpciones() {
        PreguntaOrdenarPalabras pregunta = new PreguntaOrdenarPalabras();
        List<String> nuevasOpciones = Arrays.asList("uno", "dos", "tres");

        pregunta.setOpciones(nuevasOpciones);
        List<String> opcionesObtenidas = pregunta.getOpciones();

        assertEquals(nuevasOpciones, opcionesObtenidas);
        assertNotSame(nuevasOpciones, opcionesObtenidas, "La lista devuelta debe ser una copia defensiva");
    }

    @Test
    void testComprobarRespuestaCorrectaOrdenarPalabras() {
        PreguntaOrdenarPalabras pregunta = new PreguntaOrdenarPalabras("Ordena", "uno dos tres", "dos uno tres");

        assertTrue(pregunta.comprobarRespuesta("uno dos tres"));
    }

    @Test
    void testComprobarRespuestaIncorrectaOrdenarPalabras() {
        PreguntaOrdenarPalabras pregunta = new PreguntaOrdenarPalabras("Ordena", "uno dos tres", "tres dos uno");

        assertFalse(pregunta.comprobarRespuesta("tres uno dos"));
    }

    @Test
    void testGetIdAntesDePersistencia() {
        PreguntaOrdenarPalabras pregunta = new PreguntaOrdenarPalabras();
        assertNull(pregunta.getId(), "El ID debería ser null antes de la persistencia");
    }
    
    // PreguntaRellenarPalabras Tests
    @Test
    void testConstructorConParametros() {
        PreguntaRellenarPalabras pregunta = new PreguntaRellenarPalabras("agua", "El vaso contiene ____");

        assertEquals("El vaso contiene ____", pregunta.getEnunciado());
        assertEquals("agua", pregunta.getRespuesta());
    }

    @Test
    void testConstructorVacio() {
        PreguntaRellenarPalabras pregunta = new PreguntaRellenarPalabras();

        assertNull(pregunta.getEnunciado());
        assertNull(pregunta.getRespuesta());
        assertNull(pregunta.getId());
    }

    @Test
    void testComprobarRespuestaCorrectaRellenarPalabras() {
        PreguntaRellenarPalabras pregunta = new PreguntaRellenarPalabras("sol", "El ____ brilla");

        assertTrue(pregunta.comprobarRespuesta("sol"));
    }

    @Test
    void testComprobarRespuestaIncorrectaRellenarPalabras() {
        PreguntaRellenarPalabras pregunta = new PreguntaRellenarPalabras("luna", "La ____ ilumina la noche");

        assertFalse(pregunta.comprobarRespuesta("estrella"));
    }

    @Test
    void testSettersYGetters() {
        PreguntaRellenarPalabras pregunta = new PreguntaRellenarPalabras();
        pregunta.setEnunciado("Completa: ___");
        pregunta.setRespuesta("respuesta");

        assertEquals("Completa: ___", pregunta.getEnunciado());
        assertEquals("respuesta", pregunta.getRespuesta());
    }

}
