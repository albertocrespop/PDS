package modelo;

import java.util.ArrayList;
import java.util.List;

public class Leccion {
    private String titulo;
    private String descripcion;
    private List<Pregunta> preguntas;
    private boolean completada;
    
    
   public Leccion(String titulo, String descripcion, List<Pregunta> preguntas) {
	   this.titulo = titulo;
	   this.descripcion = descripcion;
	   this.preguntas = new ArrayList<Pregunta>(preguntas);
	   this.completada = false;
   }
   
   
   //Getters
   public String getDescripcion() {
	   return descripcion;
   }
   
   public List<Pregunta> getPreguntas() {
	   return new ArrayList<Pregunta>(preguntas);
   }
   
   public String getTitulo() {
	   return titulo;
   }
   
   //Setters
   public void setCompletada(boolean completada) {
	   this.completada = completada;
   }
}
