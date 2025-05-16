package ofcourses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Curso;
import modelo.EstrategiaRepeticion;
import modelo.Leccion;
import modelo.Pregunta;
import modelo.PreguntaRellenarPalabras;
import modelo.PreguntaVF;

public class LeccionTests {
	 @Test
	    void testConstructorYGetterss() {
	        Pregunta p1 = new PreguntaVF("¿Qué es Java?", "Un lenguaje de programacion");
	        Pregunta p2 = new PreguntaVF("¿Qué es una clase?", "Una representación de un objeto");
	        List<Pregunta> preguntas = Arrays.asList(p1, p2);

	        Curso curso = new Curso(); // supondremos que tiene constructor por defecto

	        Leccion leccion = new Leccion("Intro a Java", "Lección básica", preguntas);

	        assertEquals("Intro a Java", leccion.getTitulo());
	        assertEquals("Lección básica", leccion.getDescripcion());
	        assertEquals(2, leccion.getPreguntas().size());
	        assertTrue(leccion.getPreguntas().contains(p1));
	        assertTrue(leccion.getPreguntas().contains(p2));
	        assertFalse(leccion.getCompletada());
	    }

	    @Test
	    void testSetCompletadaA() {
	        Leccion leccion = new Leccion("Test", "Prueba", new ArrayList<>());
	        assertFalse(leccion.getCompletada());

	        leccion.setCompletada(true);
	        assertTrue(leccion.getCompletada());

	        leccion.setCompletada(false);
	        assertFalse(leccion.getCompletada());
	    }

	    @Test
	    void testGetIdSinPersistencia() {
	        Leccion leccion = new Leccion("Test", "Prueba", new ArrayList<>());
	        assertNull(leccion.getId(), "El ID debe ser null antes de la persistencia");
	    }
	    
	    @Test
	    public void testConstructorYGetters() {
	        List<Pregunta> preguntas = new ArrayList<>();
	        preguntas.add(new PreguntaRellenarPalabras("respuesta1", "texto1"));
	        preguntas.add(new PreguntaRellenarPalabras("respuesta2", "texto2"));

	        Leccion leccion = new Leccion("Título", "Descripción", preguntas);

	        assertEquals("Título", leccion.getTitulo());
	        assertEquals("Descripción", leccion.getDescripcion());
	        assertEquals(2, leccion.getPreguntas().size());
	        assertFalse(leccion.getCompletada());
	        assertEquals(0, leccion.getUltimaPregunta());
	        assertEquals(preguntas.get(0), leccion.getPreguntaActual());
	    }

	    @Test
	    public void testSetCompletada() {
	        Leccion leccion = new Leccion("T", "D", new ArrayList<>());
	        leccion.setCompletada(true);
	        assertTrue(leccion.getCompletada());

	        leccion.setCompletada(false);
	        assertFalse(leccion.getCompletada());
	    }

	    @Test
	    public void testSetUltimaPregunta() {
	        Leccion leccion = new Leccion("T", "D", new ArrayList<>());
	        leccion.setUltimaPregunta(5);
	        assertEquals(5, leccion.getUltimaPregunta());
	    }

	    @Test
	    public void testSetPreguntas() {
	        Leccion leccion = new Leccion("T", "D", new ArrayList<>());
	        List<Pregunta> nuevas = new ArrayList<>();
	        nuevas.add(new PreguntaRellenarPalabras("r", "t"));
	        leccion.setPreguntas(nuevas);

	        List<Pregunta> resultado = leccion.getPreguntas();
	        assertEquals(1, resultado.size());
	        assertEquals("r", resultado.get(0).getRespuesta());
	    }

	    @Test
	    public void testGetSiguientePreguntaDentroDeRango() {
	        List<Pregunta> preguntas = new ArrayList<>();
	        preguntas.add(new PreguntaRellenarPalabras("a", "b"));
	        preguntas.add(new PreguntaRellenarPalabras("c", "d"));

	        Leccion leccion = new Leccion("T", "D", preguntas);
	        Pregunta siguiente = leccion.getSiguientePregunta();
	        assertEquals(preguntas.get(1), siguiente);
	        assertEquals(1, leccion.getUltimaPregunta());
	    }

	    @Test
	    public void testGetSiguientePreguntaFueraDeRango() {
	        List<Pregunta> preguntas = new ArrayList<>();
	        preguntas.add(new PreguntaRellenarPalabras("a", "b"));

	        Leccion leccion = new Leccion("T", "D", preguntas);
	        leccion.setUltimaPregunta(0);
	        Pregunta siguiente = leccion.getSiguientePregunta();
	        assertNull(siguiente);
	        assertEquals(1, leccion.getUltimaPregunta());
	    }

	    @Test
	    public void testAplicarEstrategia() {
	        List<Pregunta> preguntas = new ArrayList<>();
	        preguntas.add(new PreguntaRellenarPalabras("a", "b"));
	        preguntas.add(new PreguntaRellenarPalabras("c", "d"));
	        preguntas.add(new PreguntaRellenarPalabras("e", "f"));

	        Leccion leccion = new Leccion("T", "D", preguntas);

	        leccion.aplicarEstrategia(new EstrategiaRepeticion());
	        List<Pregunta> resultado = leccion.getPreguntas();

	        // Tercer elemento se repite según EstrategiaRepeticion
	        assertEquals(4, resultado.size());
	        assertEquals(resultado.get(2), resultado.get(3));
	    }
}
