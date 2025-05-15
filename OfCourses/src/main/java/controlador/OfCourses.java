package controlador;

import modelo.Usuario;

public class OfCourses {
	private static OfCourses unicaInstancia = null;
	private Usuario usuarioActual;
	
	public OfCourses() {
		
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
	
	public boolean Login() {
		
		
		return false;
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
}
