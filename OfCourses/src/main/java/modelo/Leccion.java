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
	
	private int ultimaPregunta;
	
	private boolean completada;
	
	// Constructor para hibernate
	public Leccion () {}
	
	public Leccion(String titulo, String descripcion, List<Pregunta> preguntas) {
		
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.preguntas = new ArrayList<Pregunta>(preguntas);
		this.completada = false;

		this.ultimaPregunta = 0;
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
	
	public int getUltimaPregunta() {
		return ultimaPregunta;
	}
	
	// Setters
	public void setCompletada(boolean completada) {
		this.completada = completada;
	}

	public void setUltimaPregunta(int ultimaPregunta) {
		this.ultimaPregunta = ultimaPregunta;
	}
	
	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}
	
	//Metodos de clase
	public void aplicarEstrategia(Estrategia estrategia) {
		setPreguntas(estrategia.ordenar(preguntas));
	}
	
	public Pregunta getSiguientePregunta() {
		return preguntas.get(ultimaPregunta);
	}
}
