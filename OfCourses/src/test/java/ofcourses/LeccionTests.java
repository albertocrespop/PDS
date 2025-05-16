package ofcourses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import modelo.Curso;
import modelo.Leccion;
import modelo.Pregunta;
import modelo.PreguntaVF;

public class LeccionTests {
	 @Test
	    void testConstructorYGetters() {
	        Pregunta p1 = new PreguntaVF("¿Qué es Java?", "Un lenguaje de programacion");
	        Pregunta p2 = new PreguntaVF("¿Qué es una clase?", "Una representación de un objeto");
	        List<Pregunta> preguntas = Arrays.asList(p1, p2);

	        Curso curso = new Curso(); // supondremos que tiene constructor por defecto

	        Leccion leccion = new Leccion("Intro a Java", "Lección básica", preguntas, curso);

	        assertEquals("Intro a Java", leccion.getTitulo());
	        assertEquals("Lección básica", leccion.getDescripcion());
	        assertEquals(2, leccion.getPreguntas().size());
	        assertTrue(leccion.getPreguntas().contains(p1));
	        assertTrue(leccion.getPreguntas().contains(p2));
	        assertFalse(leccion.getCompletada());
	    }

	    @Test
	    void testSetCompletada() {
	        Leccion leccion = new Leccion("Test", "Prueba", new ArrayList<>(), new Curso());
	        assertFalse(leccion.getCompletada());

	        leccion.setCompletada(true);
	        assertTrue(leccion.getCompletada());

	        leccion.setCompletada(false);
	        assertFalse(leccion.getCompletada());
	    }

	    @Test
	    void testGetIdSinPersistencia() {
	        Leccion leccion = new Leccion("Test", "Prueba", new ArrayList<>(), new Curso());
	        assertNull(leccion.getId(), "El ID debe ser null antes de la persistencia");
	    }
}
