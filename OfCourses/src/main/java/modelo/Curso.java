package modelo;

import java.util.ArrayList;
import java.util.List;


public class Curso {
    
	private String titulo;
    private String descripcion;
    private List<Leccion> lecciones;
    private int horas;
    private Leccion leccionActual;
    private int racha;
    private int rachaMaxima;
    private Estrategia estrategia;
    
    public Curso(String titulo, String descripcion, List<Leccion> lecciones, Estrategia estrategia) {
    	this.titulo = titulo;
    	this.descripcion = descripcion;
    	this.lecciones = new ArrayList<Leccion>(lecciones);
    	this.estrategia = estrategia;
    	this.horas = 0;
    	this.leccionActual = null;
    	this.racha = 0;
    	this.rachaMaxima = 0;
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
    
    public Leccion getLeccionActual() {
		return leccionActual;
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
    
    //Setters
    public void setEstrategia(Estrategia estrategia) {
		this.estrategia = estrategia;
	}
    
    public void setHoras(int horas) {
		this.horas = horas;
	}
    
    public void setLeccionActual(Leccion leccionActual) {
		this.leccionActual = leccionActual;
	}
    
    public void setRacha(int racha) {
		this.racha = racha;
	}
    
    public void setRachaMaxima(int rachaMaxima) {
		this.rachaMaxima = rachaMaxima;
	}
    
    
    
}
