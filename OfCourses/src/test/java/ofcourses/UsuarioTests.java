package ofcourses;

import modelo.*;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTests {
	
	// Usuario Tests
    @Test
    public void testCrearUsuario() {
        Usuario usuario = new Usuario("Juan", "juan@example.com", "12345");
        
        assertEquals("Juan", usuario.getUsername());
        assertEquals("juan@example.com", usuario.getEmail());
        assertEquals("12345", usuario.getPassword());
        assertNotNull(usuario.getCursos()); // Se devuelve una lista vacía no null
        assertTrue(usuario.getCursos().isEmpty());
        assertNotNull(usuario.getId());
    }
	
    @Test
    public void testSettersUsuario() {
    	Usuario usuario = new Usuario("Juan", "juan@example.com", "12345");
    	usuario.setEmail("pedro@example.com");
    	usuario.setPassword("4321");
    	usuario.setUsername("Matias");

    	assertEquals("pedro@example.com", usuario.getEmail());
    	assertEquals("4321", usuario.getPassword());
    	assertEquals("Matias", usuario.getUsername());
    	// Usuario{id=" + id + ", nombre='" + username + "', email='" + email + "'}
    	String salida = new String("Usuario{id=" + usuario.getId() + ", nombre='" + usuario.getUsername() + "', email='" + usuario.getEmail() + "'}");
    	assertEquals(salida, usuario.toString());
    }

    @Test
    public void testAgregarCursoAUsuario() {
        Usuario usuario = new Usuario("Laura", "laura@example.com", "abc");
        Curso curso = new Curso("Curso Avanzado", "Descripción", new ArrayList<>(), "modelo.EstrategiaAleatoria", usuario);

        usuario.addCurso(curso);

        assertEquals(1, usuario.getCursos().size());
        assertEquals(curso, usuario.getCursos().get(0));
        assertEquals(usuario, curso.getCreador());
    }

    // PreguntaFlashCard Tests
    @Test
    public void testPreguntaFlashCard() {
        PreguntaFlashCard pregunta = new PreguntaFlashCard("¿Qué es Java?", "Un lenguaje de programación");

        assertEquals("¿Qué es Java?", pregunta.getEnunciado());
        assertEquals("Un lenguaje de programación", pregunta.getRespuesta());
    }

    // PreguntaVF Tests
    @Test
    public void testPreguntaVF() {
        PreguntaVF pregunta = new PreguntaVF("¿El enano es gigante?", "VERDADERO");

        assertEquals("¿El enano es gigante?", pregunta.getEnunciado());
        assertEquals("VERDADERO", pregunta.getRespuesta());
    }

    // PreguntaOrdenarPalabras Tests
    @Test
    public void testPreguntaOrdenarPalabras() {
        PreguntaOrdenarPalabras pregunta = new PreguntaOrdenarPalabras(
            "Ordena palabras", "Hola Mundo Java", "Java Mundo Hola"
        );

        assertEquals("Ordena palabras", pregunta.getEnunciado());
        assertTrue(pregunta.comprobarRespuesta("Hola Mundo Java"));
        assertFalse(pregunta.comprobarRespuesta("Java Mundo Hola"));
    }
    
    // PreguntaRellenarPalabras Tests
    @Test
    public void testComprobarRespuestaCorrecta() {
        PreguntaRellenarPalabras pregunta = new PreguntaRellenarPalabras("clase", "Completa: public ____ NombreClase { }");

        assertTrue(pregunta.comprobarRespuesta("clase"), "La respuesta debería ser correcta");
        assertFalse(pregunta.comprobarRespuesta("interfaz"), "La respuesta debería ser incorrecta");
    }

}
