package ofcourses;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import modelo.*;
import persistencia.CursoRepository;
import persistencia.JPAUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersistenciaTest {
	  private static EntityManagerFactory emf;
	    private EntityManager em;
	    private CursoRepository cursoRepository;

	    @BeforeAll
	    static void setUpBeforeAll() {
	        emf = Persistence.createEntityManagerFactory("ofcoursesPU");
	    }

	    @BeforeEach
	    void setUp() {
	        em = emf.createEntityManager();
	        cursoRepository = new CursoRepository(em);
	        limpiarBaseDeDatos();
	    }

	    private void limpiarBaseDeDatos() {
	        EntityTransaction tx = em.getTransaction();
	        try {
	            if (!tx.isActive()) {
	                tx.begin();
	            }
            em.createNativeQuery("PRAGMA foreign_keys = OFF").executeUpdate();
            em.createQuery("DELETE FROM PreguntaRellenarPalabras").executeUpdate();
            em.createQuery("DELETE FROM PreguntaVF").executeUpdate();
            em.createQuery("DELETE FROM PreguntaFlashCard").executeUpdate();
            em.createQuery("DELETE FROM PreguntaOrdenarPalabras").executeUpdate();
            em.createQuery("DELETE FROM Pregunta").executeUpdate();
            em.createQuery("DELETE FROM Leccion").executeUpdate();
            em.createQuery("DELETE FROM Curso").executeUpdate();
            em.createQuery("DELETE FROM Usuario").executeUpdate();
            em.createNativeQuery("PRAGMA foreign_keys = ON").executeUpdate();
            tx.commit();
	        } catch (Exception e) {
	            if (tx.isActive()) {
	                tx.rollback();
	            }
	            throw e;
	        }
	    }

<<<<<<< Updated upstream
    @Test
    @Order(1)
    public void testPersistenciaCompleta() {
        Usuario usuario = new Usuario("Carlos", "carlos@example.com", "1234");
        List<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(new PreguntaFlashCard("¿Qué es una clase?", "Plantilla para objetos"));
        preguntas.add(new PreguntaVF("¿Java soporta múltiples herencias de clase?", "FALSO"));
        preguntas.add(new PreguntaOrdenarPalabras("Ordena palabras clave", "public static void", "void static public"));
        List<Leccion> lecciones = new ArrayList<Leccion>();
        
        Curso curso = new Curso("Curso Java Completo", "Aprende Java de forma avanzada", lecciones, "modelo.EstrategiaAleatoria",usuario);
        
        Leccion leccion = new Leccion("POO", "Conceptos de orientación a objetos", preguntas, curso);
=======
	    @AfterEach
	    void tearDown() {
	        if (em != null && em.isOpen()) {
	            em.close();
	        }
	    }
>>>>>>> Stashed changes

	    @AfterAll
	    static void tearDownAfterAll() {
	        if (emf != null && emf.isOpen()) {
	            emf.close();
	        }
	    }

<<<<<<< Updated upstream
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
    public void testBuscarCursosPorTitulo() {
        // Buscar cursos que contengan "Java" en el título
        String terminoBusqueda = "Java";
        
        TypedQuery<Curso> query = em.createQuery(
            "SELECT c FROM Curso c WHERE c.titulo LIKE :termino", Curso.class);
        query.setParameter("termino", "%" + terminoBusqueda + "%");
        
        List<Curso> cursos = query.getResultList();
        
        assertFalse(cursos.isEmpty(), "Debería encontrar al menos un curso con 'Java' en el título");
        assertTrue(cursos.get(0).getTitulo().contains(terminoBusqueda));
    }

    @Test
    @Order(4)
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
    @Order(5)
    public void testBuscarLeccionesPorCurso() {
        // Primero buscar un curso existente
        TypedQuery<Curso> cursoQuery = em.createQuery(
            "SELECT c FROM Curso c WHERE c.titulo = 'Curso Java Completo'", Curso.class);
        List<Curso> cursos = cursoQuery.getResultList();
        
        if (!cursos.isEmpty()) {
            Curso curso = cursos.get(0);
            
            // Buscar las lecciones de este curso
            TypedQuery<Leccion> leccionQuery = em.createQuery(
                "SELECT l FROM Leccion l WHERE l.curso = :curso", Leccion.class);
            leccionQuery.setParameter("curso", curso);
            
            List<Leccion> lecciones = leccionQuery.getResultList();
            
            assertFalse(lecciones.isEmpty(), "El curso debería tener lecciones asociadas");
            assertEquals(curso.getId(), lecciones.get(0).getId());
        } else {
            fail("No se encontró el curso 'Curso Java Completo' en la base de datos");
        }
    }

    @Test
    @Order(6)
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
=======
	    @Test
	    void testGuardarYRecuperarCurso() {
	        // No inicies transacción aquí, déjalo que el repositorio lo maneje
	        Curso curso = new Curso();
	        
	        cursoRepository.guardar(curso);
	        
	        Curso recuperado = cursoRepository.buscarPorId(curso.getId());
	        assertNotNull(recuperado);
	    }
	    
	    @Test
	    void testEliminarCursoExistente() {
	        // Buscar un curso por ID (más seguro que por nombre)
	    	Curso curso = new Curso();
	        
	        cursoRepository.guardar(curso);
	        curso = em.find(Curso.class, 1L);
	        
	        // Eliminar el curso
	        cursoRepository.eliminar(curso);
	        
	        // Verificar que ya no existe
	        Curso eliminado = cursoRepository.buscarPorId(1L);
	        assertNull(eliminado, "El curso debería haber sido eliminado");
	    }
	    @Test
	    void testEliminarCursoNoAdjuntado() {
	        // Crear un curso nuevo no adjuntado al EM
	        Curso nuevoCurso = new Curso();
	        
	        // Esto debería funcionar porque el repositorio hace merge si no está adjuntado
	        cursoRepository.eliminar(nuevoCurso);
	        
	        // Verificar que no se creó accidentalmente
	        Long id = nuevoCurso.getId();
	        if (id != null) {
	            assertNull(cursoRepository.buscarPorId(id), "No debería existir el curso temporal");
	        }
	    }
>>>>>>> Stashed changes
}
