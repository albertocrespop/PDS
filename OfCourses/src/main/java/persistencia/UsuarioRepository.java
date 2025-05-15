package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Usuario;

import java.util.List;

public class UsuarioRepository {

    private EntityManager em;

    public UsuarioRepository(EntityManager em) {
        this.em = em;
    }
    
    public Usuario buscarPorId(Long id) {
        return em.find(Usuario.class, id);
    }

    public Usuario buscarPorEmail(String email) {
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    public Usuario buscarPorNombre(String nombre) {
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.username = :nombre", Usuario.class);
        query.setParameter("nombre", nombre);
        return query.getSingleResult();
    }
    
    public List<Usuario> listarTodos() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    public void eliminar(Usuario usuario) {
        em.getTransaction().begin();
        em.remove(em.contains(usuario) ? usuario : em.merge(usuario));
        em.getTransaction().commit();
    }
    
    public boolean guardar(Usuario usuario) {
    	
    	List<Usuario> todos = listarTodos();
    	
    	for(Usuario u: todos) {
    		if(u.getEmail().equals(usuario.getEmail()) || u.getUsername().equals(usuario.getUsername())) {
    			return false;
    		}
    	}
    	
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
        return true;
    }
    
    public void modificarUsuario(Usuario usuario) {
        em.getTransaction().begin();
		em.merge(usuario);
        em.getTransaction().commit();
    }

}