package modelo;

import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "lecciones")
public class Leccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;
	private String descripcion;
	
	@OneToMany
	@JoinColumn(name = "leccion_id")
	private List<Pregunta> preguntas;
	
	
	private boolean completada;
	
	// Constructor para hibernate
	public Leccion () {}
	
	public Leccion(String titulo, String descripcion, List<Pregunta> preguntas) {
				
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.preguntas = new ArrayList<Pregunta>(preguntas);
		this.completada = false;
	}

	// Getters
	public Long getId() {
		return id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public List<Pregunta> getPreguntas() {
		return new ArrayList<Pregunta>(preguntas);
	}

	public String getTitulo() {
		return titulo;
	}

	public boolean getCompletada() {
		return completada;
	}

	// Setters
	public void setCompletada(boolean completada) {
		this.completada = completada;
	}
}
