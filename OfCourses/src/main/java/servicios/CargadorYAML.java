package servicios;

import modelo.*;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de cargar archivos YAML que contienen cursos y convertirlos en objetos del modelo.
 * Utiliza SnakeYAML para deserializar la estructura definida en las clases auxiliares (CursoYAML, LeccionYAML, PreguntaYAML).
 */
public class CargadorYAML {

    /**
     * Carga un archivo YAML desde el sistema de archivos y lo transforma en un objeto {@link Curso}.
     *
     * @param rutaArchivo Ruta absoluta o relativa al archivo YAML.
     * @return Objeto {@link Curso} completamente construido, o null si ocurre un error.
     */
    public Curso cargarCursoDesdeYAML(String rutaArchivo) {
        try (InputStream input = new FileInputStream(rutaArchivo)) {
            Yaml yaml = new Yaml(new Constructor(CursoYAML.class));
            CursoYAML cursoYAML = yaml.load(input);
            return construirCurso(cursoYAML);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Construye un objeto {@link Curso} a partir de una instancia auxiliar {@link CursoYAML}.
     * La clase CursoYAML es necesaria debido a la implementación de SnakeYAML: esta requiere
     * clases con constructores vacíos y tipos simples (como String o List), y no puede instanciar
     * directamente clases complejas con lógica interna, constructores con parámetros o estructuras
     * de herencia como la jerarquía de {@link Pregunta}.
     * 
     * Por ello, primero se deserializa el YAML en una estructura auxiliar simple (CursoYAML, LeccionYAML, PreguntaYAML),
     * y luego se transforma manualmente al modelo real, instanciando explícitamente cada subtipo de {@link Pregunta}
     * (como {@link PreguntaFlashCard}, {@link PreguntaRelleno}, {@link PreguntaVF}, etc.) según el tipo especificado en el archivo YAML.
     *
     * @param datos Objeto deserializado desde YAML.
     * @return Objeto {@link Curso} listo para ser usado en la aplicación.
     */
    private Curso construirCurso(CursoYAML datos) {
        String tipo = datos.getEstrategia().toLowerCase();
    	
    	List<Leccion> lecciones = datos.getLecciones().stream().map(leccionYAML -> {
            List<Pregunta> preguntas = leccionYAML.getPreguntas().stream().map(p -> {
                return crearPreguntaDesdeYAML(p);
            }).toList();
            return new Leccion(leccionYAML.getTitulo(), leccionYAML.getDescripcion(), preguntas);
        }).toList();

    	Curso curso = new Curso(datos.getTitulo(), datos.getDescripcion(), lecciones, tipo);         
    	
        return curso;// TODO: Llamar al controlador para meter el usuario actual
    }

    /**
     * Crea una instancia concreta de una subclase de {@link Pregunta} según el tipo especificado como texto.
     * Este método actúa como una fábrica básica que interpreta el campo "tipo" del archivo YAML
     * y construye la pregunta correspondiente (por ejemplo: {@link PreguntaFlashCard}, {@link PreguntaRelleno}, etc.).
     *
     * @param p Objeto {@link PreguntaYAML} que contiene los datos básicos de la pregunta.
     * @return Instancia específica de {@link Pregunta} lista para añadirse a la lección.
     */
    private Pregunta crearPreguntaDesdeYAML(PreguntaYAML p) {
        String tipo = p.getTipo().toLowerCase();

        switch (tipo) {
            case "flashcard":
                return new PreguntaFlashCard(p.getTexto(), p.getRespuesta());
            case "relleno":
                return new PreguntaRellenarPalabras(p.getTexto(), p.getRespuesta());
            case "vf":
                return new PreguntaVF(p.getTexto(), p.getRespuesta());
            case "ordenar":
                return new PreguntaOrdenarPalabras(p.getTexto(), p.getRespuesta(), p.getOpciones());
            default:
                // Por defecto, usar flashcard si el tipo no es reconocido
                return new PreguntaFlashCard(p.getTexto(), p.getRespuesta());
        }
    }
}