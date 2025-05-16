package modelo;


import java.time.LocalDateTime;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario {
	
	private static final int MAX_VIDAS = 5;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 
	
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int vidas;

    private LocalDate ultimaRecarga;
    
    
    @OneToMany(mappedBy="autor")
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
        this.vidas = MAX_VIDAS;
        this.setUltimaRecarga(LocalDate.now());
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
    
    public int getVidas() {
		return vidas;
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
 
    public void setUltimaRecarga(LocalDate ultimaRecarga) {
		this.ultimaRecarga = ultimaRecarga;
	}
    public void setUltimaFecha(LocalDateTime fecha) {
		this.ultimoDia = fecha;
	}
 
    
    public void addCurso(Curso c) {
    	cursos.add(c);
    }
    
    /**
     * Recarga las vidas del usuario si ha pasado al menos un día desde la última recarga.
     * 
     * Este método compara la fecha actual con la fecha almacenada en {@code ultimaRecarga}.
     * Si no son iguales (es decir, ha cambiado el día), restablece las vidas al valor máximo
     * definido por {@code MAX_VIDAS} y actualiza {@code ultimaRecarga} a la fecha actual.
     * 
     * @return {@code true} si se recargaron las vidas; {@code false} en caso contrario.
     */
    public boolean recargarSiEsNuevoDia() {
        LocalDate hoy = LocalDate.now();
        if (!ultimaRecarga.equals(hoy)) {
            this.vidas = MAX_VIDAS;
            this.ultimaRecarga = hoy;
            return true;
        }
        return false;
    }
    
    /**
     * Resta una vida al usuario si todavía le quedan vidas disponibles.
     * 
     * Este método no permite que el contador de vidas sea menor que cero.
     * Puede utilizarse tras cada respuesta incorrecta durante la realización de un curso.
     */
    public void perderVida() {
    	if(vidas > 0) {
    		vidas--;
    	}
    }
    
    /**
     * Indica si el usuario se ha quedado sin vidas.
     *
     * @return {@code true} si el número de vidas actuales es 0; {@code false} en caso contrario.
     */
    public boolean estaSinVidas() {
        return vidas == 0;
    }
    public void setFoto(String foto) {
		this.foto = foto;
	}
    
    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + username + "', email='" + email + "'}";
    }
    
    public Curso getCurso(String cursoActual) {
		for(Curso c: cursos) {
			if(c.getTitulo().equals(cursoActual)) {
				return c;
			}
		}
		return null;
	}
}