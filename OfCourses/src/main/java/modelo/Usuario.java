package modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String username;
    private String email;
    private String password;
    private int horasTotales;
    private int rachaMaxima;
    private List<Curso> cursos;

    public Usuario(String username, String email, String password) {
    	this.username = username;
    	this.email = email;
    	this.password = password;
    	this.horasTotales = 0;
    	this.rachaMaxima = 0;
    	this.cursos = new ArrayList<Curso>();
    }
    
    
    
    //Getters
    public List<Curso> getCursos() {
		return new ArrayList<Curso>(cursos);
	}
    
    public String getEmail() {
		return email;
	}
    
    
    public int getHorasTotales() {
		return horasTotales;
	}
    
    public String getPassword() {
		return password;
	}
    
    public int getRachaMaxima() {
		return rachaMaxima;
	}
    
    public String getUsername() {
		return username;
	}
    
    //Setters
    public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
    
    public void setEmail(String email) {
		this.email = email;
	}
    
    
    public void setHorasTotales(int horasTotales) {
		this.horasTotales = horasTotales;
	}
    
    public void setPassword(String password) {
		this.password = password;
	}
    
    public void setRachaMaxima(int rachaMaxima) {
		this.rachaMaxima = rachaMaxima;
	}
    
    public void setUsername(String username) {
		this.username = username;
	}
   
}