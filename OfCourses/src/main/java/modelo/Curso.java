package modelo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "cursos")
public class Curso {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String titulo;
    private String descripcion;
    
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leccion> lecciones;
    
    private int leccionActual;
    
    private int horas;
    private int racha;
    private int rachaMaxima;
    
    @Transient
    private Estrategia estrategia;
    
    private String estrategiaString;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario creador;
    
    public Curso() {}
    
    public Curso(String titulo, String descripcion, List<Leccion> lecciones, String estrategia, Usuario user) {
    	    	
    	this.creador = user;
    	this.estrategiaString = estrategia;

    	this.titulo = titulo;
    	this.descripcion = descripcion;
    	this.lecciones = new ArrayList<Leccion>(lecciones);
    	try {
			this.estrategia = (Estrategia) Class.forName(estrategiaString).getDeclaredConstructor(null).newInstance(null);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.horas = 0;
    	this.racha = 0;
    	this.rachaMaxima = 0;
    	this.leccionActual = 0;
    }
    
    //Getters
    public String getDescripcion() {
		return descripcion;
	}
    
    public Estrategia getEstrategia() {
		return estrategia;
	}
    
    public int getHoras() {
		return horas;
	}
    
    
    public List<Leccion> getLecciones() {
		return new ArrayList<Leccion>(lecciones);
	}
    
    public int getRacha() {
		return racha;
	}
    
    public int getRachaMaxima() {
		return rachaMaxima;
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
    
    public Usuario getCreador() {
		return creador;
	}
    
    //Setters
    public void setEstrategia(Estrategia estrategia) {
		this.estrategia = estrategia;
	}
    
    public void setHoras(int horas) {
		this.horas = horas;
	}
    
    
    public void setRacha(int racha) {
		this.racha = racha;
	}
    
    public void setRachaMaxima(int rachaMaxima) {
		this.rachaMaxima = rachaMaxima;
	}
    
    public void setEstrategiaString(String estrategiaString) {
		this.estrategiaString = estrategiaString;
	}
    
    public void setLeccionActual(int leccionActual) {
		this.leccionActual = leccionActual;
	}
    
    
    
}
