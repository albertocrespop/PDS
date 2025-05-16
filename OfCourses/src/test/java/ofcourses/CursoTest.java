package ofcourses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import modelo.Curso;
import modelo.Estrategia;
import modelo.EstrategiaRepeticion;
import modelo.Leccion;
import modelo.Usuario;

public class CursoTest {
	@Test
    public void testAgregarLeccionACurso() {
        Usuario usuario = new Usuario("Pedro", "pedro@example.com", "123", null);
        Curso curso = new Curso("Curso Test", "Descripción test", new ArrayList<>(), "modelo.EstrategiaAleatoria");

        assertEquals("Curso Test", curso.getTitulo());
        assertEquals("Descripción test", curso.getDescripcion());
        assertNotNull(curso.getLecciones());
        assertTrue(curso.getLecciones().isEmpty());
        curso.setAutor(usuario);
        assertEquals(usuario, curso.getAutor());
    }
    
    @Test
    public void testGettersSettersCurso() {
        List<Leccion> lecciones = new ArrayList<Leccion>();
    	Curso curso = new Curso("Java", "Curso de Java", lecciones, "modelo.EstrategiaAleatoria");
    	
    	assertNotNull(curso.getId());
        assertEquals("Curso de Java", curso.getDescripcion());
        curso.setHoras(5);
        assertEquals(5, curso.getHoras());
        curso.setLeccionActual(2);
        assertEquals(2, curso.getLeccionActual());
        curso.setEstrategiaString("modelo.OtraEstrategia");
        assertEquals("modelo.OtraEstrategia", curso.getEstrategiaString());
        List<Leccion> nuevasLecciones = new ArrayList<>();
        nuevasLecciones.add(new Leccion());
        curso.setLecciones(nuevasLecciones);
        assertEquals(1, curso.getLecciones().size());
        Estrategia estrategia = new EstrategiaRepeticion();
        curso.setEstrategia(estrategia);
        assertEquals(estrategia, curso.getEstrategia());
    }
}
