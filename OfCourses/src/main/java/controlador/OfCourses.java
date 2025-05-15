package controlador;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jakarta.persistence.EntityManager;
import javafx.scene.image.Image;
import modelo.Curso;
import modelo.Usuario;
import persistencia.CursoRepository;
import persistencia.JPAUtil;
import persistencia.UsuarioRepository;
import servicios.CargadorYAML;

public class OfCourses {
	public static final String FOTO_DEFECTO = "/main/resources/imagenes/foto-perfil-default.png";
	private static OfCourses unicaInstancia = null;
	
	private UsuarioRepository repoUser;
	private CursoRepository repoCurso;

	private EntityManager em = JPAUtil.getEntityManager();
	
	private Usuario usuarioActual;
	
	private OfCourses() {
		repoUser = new UsuarioRepository(em);
		repoCurso = new CursoRepository(em);
	}
	
	
	/** Devuelve la única instancia de controlador (patrón Singleton)
	 * 
	 *	@return Única instancia
	 */
	public static OfCourses getUnicaInstancia() {
		if(unicaInstancia == null) {
			unicaInstancia = new OfCourses();
		}
		return unicaInstancia;
	}
	
	public boolean Login(String user, String password) {
		
		if(user.contains("@")) {
			usuarioActual = repoUser.buscarPorEmail(user);
		}else {
			usuarioActual = repoUser.buscarPorNombre(user);
		}
		
		if(usuarioActual == null || !usuarioActual.getPassword().equals(password)) {
			usuarioActual = null;
			return false;
		}else{
			return true;
		}
	}
	
	public void recargarVidasSiCorresponde() {
	    usuarioActual.recargarSiEsNuevoDia();
	}
	
	public void perderVida() {
	    usuarioActual.perderVida();
	}
	
	public boolean tieneVidas() {
	    return !usuarioActual.estaSinVidas();
	}
	
	public int getVidas() {
	    return 3; // TODO: Cambiar a usuario.getVidas() cuando usuario sea persistente
	}


	public boolean registerUser(String username, String password, String email, String url) {
		
		Usuario user = new Usuario(username, email, password, url);
		
		if(!repoUser.guardar(user)) {
			return false;
		}
		
		return true;
	}
	
	public Image getFotoUsuarioActual() {
		
		Image imagen;
		
		try{
			imagen = new Image(usuarioActual.getFoto());
		}catch (Exception e) {
			imagen = new Image(getClass().getResource(FOTO_DEFECTO).toString());
		}
		
		return imagen;
	}


	public boolean addCurso(String file) {
		CargadorYAML cargador = new CargadorYAML();
		

		try{
			Curso curso = cargador.cargarCursoDesdeArchivo(file);
			usuarioActual.addCurso(curso);
	        em.getTransaction().begin();
	        em.persist(curso);
	        em.getTransaction().commit();		
	    } catch (IOException e) {
			return false;
		}
		
		repoUser.modificarUsuario(usuarioActual);
		return true;
	}


	public List<Curso> getCursosDisponibles() {
		return usuarioActual.getCursos();
	}
	
	
}
