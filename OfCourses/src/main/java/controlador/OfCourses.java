package controlador;

public class OfCourses {
	private OfCourses unicaInstancia = null;
	
	
	public OfCourses() {
		
	}
	
	
	/** Devuelve la única instancia de controlador (patrón Singleton)
	 * 
	 *	@return Única instancia
	 */
	public OfCourses getUnicaInstancia() {
		if(unicaInstancia == null) {
			unicaInstancia = new OfCourses();
		}
		return unicaInstancia;
	}
}
