package persistencia;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Curso;
import modelo.Leccion;
import modelo.Usuario;

public class CursoRepository {
    private EntityManager em;
    
    private LeccionRepository repoLeccion;

    public CursoRepository(EntityManager em) {
        this.em = em;
        repoLeccion = new LeccionRepository(em);
    }
    
    public Curso buscarPorId(Long id) {
        return em.find(Curso.class, id);
    }

    public void eliminar(Curso curso) {
        em.getTransaction().begin();
        em.remove(em.contains(curso) ? curso : em.merge(curso));
        em.getTransaction().commit();
    }
    
    public void guardar(Curso curso) {
        em.getTransaction().begin();
        em.persist(curso);
        em.getTransaction().commit();        
    }
    
    public void modificarCurso(Curso curso) {
        em.getTransaction().begin();
		em.merge(curso);
        em.getTransaction().commit();
    }
}
