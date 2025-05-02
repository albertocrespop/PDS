package modelo;

import java.util.ArrayList;

import java.util.List;

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
    
    
    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Curso> cursos;
    
    public Usuario() {}

    public Usuario(String username, String email, String password) {
    	this.username = username;
    	this.email = email;
    	this.password = password;
    //	this.cursos = new ArrayList<Curso>();
    }
    
    
    
    //Getters
  /*  public List<Curso> getCursos() {
		return new ArrayList<Curso>(cursos);
	}
    */
    public String getEmail() {
		return email;
	}
    
    
 /*   public int getHorasTotales() {
		return horasTotales;
	}*/
    
    public String getPassword() {
		return password;
	}
    
  /*  public int getRachaMaxima() {
		return rachaMaxima;
	}*/
    
    public String getUsername() {
		return username;
	}
    
    public long getId() {
		return id;
	}
    
    //Setters
  /*  public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}*/
    
    public void setEmail(String email) {
		this.email = email;
	}
    
    
  /*  public void setHorasTotales(int horasTotales) {
		this.horasTotales = horasTotales;
	}
    */
    public void setPassword(String password) {
		this.password = password;
	}
    
  /*  public void setRachaMaxima(int rachaMaxima) {
		this.rachaMaxima = rachaMaxima;
	}*/
    
    public void setUsername(String username) {
		this.username = username;
	}
 
    
    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + username + "', email='" + email + "'}";
    }
   
}