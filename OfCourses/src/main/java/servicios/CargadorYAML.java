package servicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import modelo.Curso;

import java.io.File;
import java.io.IOException;

/**
 * Servicio encargado de cargar archivos YAML que contienen información de cursos
 * y convertirlos automáticamente en objetos del modelo {@link Curso}, utilizando la librería Jackson.
 * <p>
 * Esta clase hace uso de {@link ObjectMapper} con {@link YAMLFactory} para deserializar directamente
 * archivos YAML en la jerarquía de clases del dominio (Curso, Leccion, Pregunta y sus subtipos).
 * </p>
 *
 * <p>Requiere que las clases del modelo estén correctamente anotadas con:</p>
 * <ul>
 *     <li>{@code @JsonTypeInfo} y {@code @JsonSubTypes} para las jerarquías con herencia.</li>
 *     <li>Constructores públicos sin argumentos, o uso de getters/setters si los campos son privados.</li>
 * </ul>
 */
public class CargadorYAML {
	
	/** Manejador de deserialización para archivos YAML */
    private final ObjectMapper yamlMapper;

    /**
     * Constructor que inicializa el {@link ObjectMapper} con una fábrica para YAML.
     */
    public CargadorYAML() {
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
    }
    
    /**
     * Carga un archivo YAML desde la ruta especificada y lo convierte en un objeto {@link Curso}.
     *
     * @param rutaArchivo Ruta absoluta o relativa al archivo YAML.
     * @return Objeto {@link Curso} completamente deserializado, listo para su uso en la aplicación.
     * @throws IOException si ocurre un error al leer o parsear el archivo.
     */
    public Curso cargarCursoDesdeArchivo(String rutaArchivo) throws IOException {
        return yamlMapper.readValue(new File(rutaArchivo), Curso.class);
    }
}