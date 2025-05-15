package modelo;


import java.time.LocalDateTime;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 
	
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    
    @OneToMany
    @JoinColumn(name="autor_id")
    private List<Curso> cursos;
    
    private String foto;
    
    private LocalDateTime ultimoDia;
    private int racha;
    private int rachaMaxima;
    
    public Usuario() {}

    public Usuario(String username, String email, String password, String foto) {
    	this.username = username;
    	this.email = email;
    	this.password = password;
    	this.foto = foto;
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
		return  cursos.stream()
				.collect(Collectors.summingInt(Curso::getHoras));
	}
    
    public String getPassword() {
		return password;
	}
    
    public int getRacha() {
		return racha;
	}
    
    public int getRachaMaxima() {
		return rachaMaxima;
	}
    
    public String getUsername() {
		return username;
	}
    
    public long getId() {
		return id;
	}
    
    public void setUltimoDia(LocalDateTime ultimoDia) {
		this.ultimoDia = ultimoDia;
	}
    
    public String getFoto() {
		return foto;
	}
    //Setters
    public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
    
    public void setEmail(String email) {
		this.email = email;
	}
    
    
    public void setPassword(String password) {
		this.password = password;
	}
    
    public void setRacha(int racha) {
		this.racha = racha;
	}
    
    public void setRachaMaxima(int rachaMaxima) {
		this.rachaMaxima = rachaMaxima;
	}
    
    public void setUsername(String username) {
		this.username = username;
	}
 
    public void setUltimaFecha(LocalDateTime fecha) {
		this.ultimoDia = fecha;
	}
 
    
    public void addCurso(Curso c) {
    	cursos.add(c);
    }
    
    public void setFoto(String foto) {
		this.foto = foto;
	}
    
    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + username + "', email='" + email + "'}";
    }
   
}