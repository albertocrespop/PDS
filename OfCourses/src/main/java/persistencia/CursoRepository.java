package persistencia;


import jakarta.persistence.EntityManager;
import modelo.Curso;
import modelo.Usuario;

public class CursoRepository {
    private EntityManager em;

    public CursoRepository(EntityManager em) {
        this.em = em;
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
    
    public void modificarUsuario(Curso curso) {
        em.getTransaction().begin();
		em.merge(curso);
        em.getTransaction().commit();
    }
}
