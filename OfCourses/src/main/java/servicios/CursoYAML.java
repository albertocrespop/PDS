package servicios;
import java.util.List;

/**
 * Clase auxiliar utilizada para mapear la estructura de un archivo YAML a objetos Java.
 * Representa un curso con sus atributos básicos y una lista de lecciones.
 * Esta clase es usada por SnakeYAML para deserializar el archivo.
 */
public class CursoYAML {
	/** Título del curso */
    private String titulo;

    /** Descripción breve del curso */
    private String descripcion;

    /** Nombre de la estrategia (secuencial, aleatoria, repetición) */
    private String estrategia;

    /** Lista de lecciones que componen el curso */
    private List<LeccionYAML> lecciones;

    /**
     * Obtiene el título del curso.
     * @return el título como String
     */
    public String getTitulo() { return titulo; }

    /**
     * Establece el título del curso.
     * @param titulo el nuevo título
     */
    public void setTitulo(String titulo) { this.titulo = titulo; }

    /**
     * Obtiene la descripción del curso.
     * @return la descripción como String
     */
    public String getDescripcion() { return descripcion; }

    /**
     * Establece la descripción del curso.
     * @param descripcion la nueva descripción
     */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /**
     * Obtiene el nombre de la estrategia aplicada al curso.
     * @return estrategia como String
     */
    public String getEstrategia() { return estrategia; }

    /**
     * Establece el nombre de la estrategia del curso.
     * @param estrategia nombre de la estrategia
     */
    public void setEstrategia(String estrategia) { this.estrategia = estrategia; }

    /**
     * Obtiene la lista de lecciones del curso.
     * @return lista de objetos LeccionYAML
     */
    public List<LeccionYAML> getLecciones() { return lecciones; }

    /**
     * Establece la lista de lecciones para el curso.
     * @param lecciones nueva lista de lecciones
     */
    public void setLecciones(List<LeccionYAML> lecciones) { this.lecciones = lecciones; }
}