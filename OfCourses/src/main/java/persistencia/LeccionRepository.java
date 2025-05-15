package persistencia;

import jakarta.persistence.EntityManager;
import modelo.Leccion;

public class LeccionRepository {
	private EntityManager em;

	public LeccionRepository(EntityManager em) {
	        this.em = em;
	    }

	public Leccion buscarPorId(Long id) {
		return em.find(Leccion.class, id);
	}

	public void eliminar(Leccion leccion) {
		em.getTransaction().begin();
		em.remove(em.contains(leccion) ? leccion : em.merge(leccion));
		em.getTransaction().commit();
	}

	public void guardar(Leccion leccion) {
		em.getTransaction().begin();
		em.persist(leccion);
		em.getTransaction().commit();
	}

	public void modificarUsuario(Leccion leccion) {
		em.getTransaction().begin();
		em.merge(leccion);
		em.getTransaction().commit();
	}
}
