package modelo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "cursos")
public class Curso {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String titulo;
    private String descripcion;
	
    @OneToMany
    @JoinColumn(name="curso_id")
    private List<Leccion> lecciones;
    
    private int leccionActual;
    
    private int horas;
    
    @Transient
    private Estrategia estrategia;
    
    @JsonProperty("estrategia")
    private String estrategiaString;
    
    
    // Constructor para hibernate
    public Curso() {}
    
    public Curso(@JsonProperty("titulo") String titulo,
    	    @JsonProperty("descripcion") String descripcion,
    	    @JsonProperty("lecciones") List<Leccion> lecciones,
    	    @JsonProperty("estrategia") String estrategia) {

    	this.estrategiaString = estrategia;

    	this.titulo = titulo;
    	this.descripcion = descripcion;
    	this.lecciones = new ArrayList<Leccion>(lecciones);
		/*if (estrategia.isEmpty()) {
			estrategiaString = estrategia.substring(0, 1).toUpperCase() + estrategiaString.substring(1);
			estrategiaString = "Estrategia" + estrategiaString;
			try {
				this.estrategia = (Estrategia) Class.forName(estrategiaString).getDeclaredConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException
					| ClassNotFoundException e) {
			}
			aplicarEstrategia();
		}*/
		/*try {
			this.estrategia = (Estrategia) Class.forName("modelo."+estrategiaString).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	this.horas = 0;
    	this.leccionActual = 0;
    	
    	
    }

	//Getters
    public String getDescripcion() {
		return descripcion;
	}
    
    @JsonIgnore
    public Estrategia getEstrategia() {
		return estrategia;
	}
    
    public int getHoras() {
		return horas;
	}
    
    public List<Leccion> getLecciones() {
		return new ArrayList<Leccion>(lecciones);
	}

    public String getTitulo() {
		return titulo;
	}
    
    public long getId() {
		return id;
	}
    
    public String getEstrategiaString() {
		return estrategiaString;
	}
    
    public int getLeccionActual() {
		return leccionActual;
	}
    
    //Setters
    @JsonIgnore
    public void setEstrategia(Estrategia estrategia) {
		this.estrategia = estrategia;
	}
    
    public void setHoras(int horas) {
		this.horas = horas;
	}
    
    public void setEstrategiaString(String estrategiaString) {
		this.estrategiaString = estrategiaString;
	}
    
    public void setLeccionActual(int leccionActual) {
		this.leccionActual = leccionActual;
	}
    
    public void setLecciones(List<Leccion> lecciones) {
		this.lecciones = lecciones;
	}
    
    //Metodos auxiliares
    private void aplicarEstrategia() {
    	lecciones.stream()
    			 .forEach(l -> l.aplicarEstrategia(this.estrategia));
	}

    
}
