package servicios;

import modelo.*;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.InputStream;
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
     * Construye un objeto {@link Curso} a partir de una instancia auxiliar {@link CursoYAML}. La clase CursoYAML es necesaria
     * debido a la implementación de SnakeYAML: esta requiere clases con constructores vacíos y tipos simples (como String o List),
     * y no puede instanciar clases complejas con lógica interna, constructores con parámetros o atributos abstractos como {@link Estrategia}.
     * Por eso, primero se deserializa el YAML en una estructura auxiliar simple, y luego se transforma manualmente al modelo real.
     *
     * @param datos Objeto deserializado desde YAML.
     * @return Objeto {@link Curso} listo para ser usado en la aplicación.
     */
    private Curso construirCurso(CursoYAML datos) {
        List<Leccion> lecciones = datos.getLecciones().stream().map(leccionYAML -> {
            List<Pregunta> preguntas = leccionYAML.getPreguntas().stream().map(p ->
                new Pregunta(
                    p.getTexto(),
                    p.getRespuesta(),
                    crearTipo(p.getTipo()))
            ).toList();
            return new Leccion(leccionYAML.getTitulo(), leccionYAML.getDescripcion(), preguntas);
        }).toList();

        Estrategia estrategia;
        String tipo = datos.getEstrategia().toLowerCase();
        switch (tipo) {
            case "aleatoria":
                estrategia = new EstrategiaAleatoria();
                break;
            case "repeticion":
                estrategia = new EstrategiaRepeticion();
                break;
            case "secuencial":
                estrategia = new EstrategiaSecuencial();
                break;
            default:
                estrategia = new EstrategiaSecuencial();	// Si la estrategia no está bien definida, se pone como secuencial
                break;
        }

        return new Curso(datos.getTitulo(), datos.getDescripcion(), lecciones, estrategia);
    }

    /**
     * Crea una instancia concreta de {@link TipoEvaluacion} según el tipo especificado como texto.
     *
     * @param tipo Nombre del tipo de evaluación (flashcard, relleno, vf, ordenar, etc.).
     * @return Instancia correspondiente de {@link TipoEvaluacion}.
     */
    private TipoEvaluacion crearTipo(String tipo) {
        tipo = tipo.toLowerCase();
        TipoEvaluacion evaluador;

        switch (tipo) {
            case "flashcard":
                evaluador = new TipoFlashCard();
                break;
            case "relleno":
                evaluador = new TipoRelleno();
                break;
            case "vf":
                evaluador = new TipoVF();
                break;
            case "ordenar":
                evaluador = new TipoOrdenar();
                break;
            default:
                evaluador = new TipoFlashCard(); // Por defecto
                break;
        }

        return evaluador;
    }
}