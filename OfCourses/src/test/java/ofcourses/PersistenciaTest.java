package ofcourses;


import jakarta.persistence.EntityManager;
import modelo.*;

import persistencia.JPAUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;



public class PersistenciaTest {

    private static EntityManager em;

    @BeforeAll
    public static void init() {
        em = JPAUtil.getEntityManager();
    }

    @AfterAll
    public static void cleanup() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        JPAUtil.cerrar();
    }

    @Test
    public void testPersistenciaCompleta() {
        Usuario usuario = new Usuario("Carlos", "carlos@example.com", "1234");
        List<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(new PreguntaFlashCard("¿Qué es una clase?", "Plantilla para objetos"));
        preguntas.add(new PreguntaVF("¿Java soporta múltiples herencias de clase?", "FALSO"));
        preguntas.add(new PreguntaOrdenarPalabras("Ordena palabras clave", "public static void", "void static public"));
        List<Leccion> lecciones = new ArrayList<Leccion>();
        
        Curso curso = new Curso("Curso Java Completo", "Aprende Java de forma avanzada", lecciones, "EstrategiaAleatoria",usuario);
        
        Leccion leccion = new Leccion("POO", "Conceptos de orientación a objetos", preguntas, curso);

        lecciones.add(leccion);
        curso.setLecciones(lecciones);
        usuario.addCurso(curso);

        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();

        Usuario u = em.find(Usuario.class, usuario.getId());
        assertNotNull(u);
        assertEquals("Carlos", u.getUsername());

        List<Curso> cursos = u.getCursos();
        assertEquals(1, cursos.size());

        Curso c = cursos.get(0);
        assertEquals("Curso Java Completo", c.getTitulo());
        assertEquals(1, c.getLecciones().size());

        Leccion l = c.getLecciones().get(0);
        assertEquals("POO", l.getTitulo());
        assertEquals(3, l.getPreguntas().size());
    }
}
