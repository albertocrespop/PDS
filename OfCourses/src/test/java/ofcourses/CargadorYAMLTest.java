package ofcourses;

import modelo.Curso;
import modelo.Estrategia;
import modelo.Leccion;
import modelo.Pregunta;
import modelo.PreguntaFlashCard;
import servicios.CargadorYAML;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class CargadorYAMLTest {

    @Test
    void testCargaCursoDesdeArchivo() throws IOException {
        // Obtener ruta al recurso de prueba
        URL resource = Test.class.getResource("/curso-prueba.yaml");
        assertNotNull(resource, "No se pudo encontrar el archivo YAML de prueba");

        String rutaArchivo = null;
		try {
			rutaArchivo = Paths.get(resource.toURI()).toString();
		} catch (URISyntaxException e) {
			fail("Ruta del recurso inválida: " + e.getMessage());
		}

        // Cargar el curso
        CargadorYAML cargador = new CargadorYAML();
        Curso curso = cargador.cargarCursoDesdeArchivo(rutaArchivo);

        // Verificaciones
        assertNotNull(curso);
        assertEquals("Curso de prueba 17", curso.getTitulo());
        assertEquals("Curso básico para test", curso.getDescripcion());
        assertEquals(1, curso.getLecciones().size());

        Leccion leccion = curso.getLecciones().get(0);
        assertNotNull(leccion);
        assertEquals("Lección 17", leccion.getTitulo());
        assertEquals(1, leccion.getPreguntas().size());

        Pregunta pregunta = leccion.getPreguntas().get(0);
        assertNotNull(pregunta);
        assertTrue(pregunta instanceof PreguntaFlashCard);
        assertEquals("¿Qué es Java?", pregunta.getEnunciado());
        
        curso.aplicarEstrategia();
        Estrategia estrategia = curso.getEstrategia();
        assertNotNull(estrategia);
    }
}