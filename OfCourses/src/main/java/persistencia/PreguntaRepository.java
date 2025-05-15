package persistencia;

import jakarta.persistence.EntityManager;
import modelo.Pregunta;

public class PreguntaRepository {
	private EntityManager em;

	public PreguntaRepository(EntityManager em) {
	        this.em = em;
	    }

	public Pregunta buscarPorId(Long id) {
		return em.find(Pregunta.class, id);
	}

	public void eliminar(Pregunta pregunta) {
		em.getTransaction().begin();
		em.remove(em.contains(pregunta) ? pregunta : em.merge(pregunta));
		em.getTransaction().commit();
	}

	public void guardar(Pregunta pregunta) {
		em.getTransaction().begin();
		em.persist(pregunta);
		em.getTransaction().commit();
	}

	public void modificarUsuario(Pregunta pregunta) {
		em.getTransaction().begin();
		em.merge(pregunta);
		em.getTransaction().commit();
	}
}
