package ofcourses;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.*;

import persistencia.JPAUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersistenciaTest {

    private static EntityManager em;

    @BeforeAll
    public static void init() {
        em = JPAUtil.getEntityManager();
        em.getTransaction().begin(); // Abrimos una transacción global
    }

    @AfterAll
    public static void cleanup() {
        try {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback(); // Revertimos TODO lo que se hizo
                }
                em.clear();
                em.close();
            }
        } finally {
            JPAUtil.cerrar();
        }
    }

    @Test
    @Order(1)
    public void testPersistenciaCompleta() {
        Usuario usuario = new Usuario("Carlos", "carlos@example.com", "1234");
        List<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(new PreguntaFlashCard("¿Qué es una clase?", "Plantilla para objetos"));
        preguntas.add(new PreguntaVF("¿Java soporta múltiples herencias de clase?", "FALSO"));
        preguntas.add(new PreguntaOrdenarPalabras("Ordena palabras clave", "public static void"));
        List<Leccion> lecciones = new ArrayList<Leccion>();
        
        Curso curso = new Curso("Curso Java Completo", "Aprende Java de forma avanzada", lecciones, "modelo.EstrategiaAleatoria",usuario);
        
        Leccion leccion = new Leccion("POO", "Conceptos de orientación a objetos", preguntas, curso);

        lecciones.add(leccion);
        curso.setLecciones(lecciones);
        usuario.addCurso(curso);

        //em.getTransaction().begin();
        em.persist(usuario);
        //em.getTransaction().commit();

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
    
    @Test
    @Order(2)
    public void testBuscarUsuarioPorEmail() {
        // Buscar un usuario por su email (debería existir en la BD)
        String emailBuscado = "carlos@example.com";
        
        TypedQuery<Usuario> query = em.createQuery(
            "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
        query.setParameter("email", emailBuscado);
        
        List<Usuario> resultados = query.getResultList();
        
        assertFalse(resultados.isEmpty(), "Debería encontrar al menos un usuario con ese email");
        assertEquals(emailBuscado, resultados.get(0).getEmail());
    }

    @Test
    @Order(3)
    public void testContarPreguntasPorTipo() {
        // Contar preguntas de cada tipo en la base de datos
        TypedQuery<Long> flashCardQuery = em.createQuery(
            "SELECT COUNT(p) FROM PreguntaFlashCard p", Long.class);
        TypedQuery<Long> vfQuery = em.createQuery(
            "SELECT COUNT(p) FROM PreguntaVF p", Long.class);
        TypedQuery<Long> ordenarQuery = em.createQuery(
            "SELECT COUNT(p) FROM PreguntaOrdenarPalabras p", Long.class);
        
        Long totalFlashCards = flashCardQuery.getSingleResult();
        Long totalVF = vfQuery.getSingleResult();
        Long totalOrdenar = ordenarQuery.getSingleResult();
        
        assertNotNull(totalFlashCards);
        assertNotNull(totalVF);
        assertNotNull(totalOrdenar);
        
        System.out.println("Resumen de preguntas:");
        System.out.println("FlashCards: " + totalFlashCards);
        System.out.println("Verdadero/Falso: " + totalVF);
        System.out.println("Ordenar palabras: " + totalOrdenar);
    }


    @Test
    @Order(4)
    public void testBuscarCursosDeUsuario() {
        // Buscar un usuario específico
        String emailUsuario = "carlos@example.com";
        
        TypedQuery<Usuario> usuarioQuery = em.createQuery(
            "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
        usuarioQuery.setParameter("email", emailUsuario);
        
        List<Usuario> usuarios = usuarioQuery.getResultList();
        
        if (!usuarios.isEmpty()) {
            Usuario usuario = usuarios.get(0);
            
            // Obtener los cursos del usuario
            List<Curso> cursos = usuario.getCursos();
            
            assertFalse(cursos.isEmpty(), "El usuario debería tener cursos asociados");
            assertEquals(usuario.getId(), cursos.get(0).getCreador().getId());
        } else {
            fail("No se encontró el usuario con email " + emailUsuario);
        }
    }
}
