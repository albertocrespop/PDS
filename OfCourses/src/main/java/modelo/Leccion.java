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
	
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "leccion_id")  // crea FK en la tabla de preguntas
	private List<Pregunta> preguntas;
	
	
	private boolean completada;
	
	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;

	public Leccion(String titulo, String descripcion, List<Pregunta> preguntas, Curso curso) {
				
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.preguntas = new ArrayList<Pregunta>(preguntas);
		this.completada = false;
		this.curso = curso;
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
