package main;

import jakarta.persistence.EntityManager;
import modelo.Usuario;
import persistencia.JPAUtil;

public class Tests {

	public static void main(String[] args) {
		 EntityManager em = JPAUtil.getEntityManager();

	        Usuario usuario = new Usuario("Ana Pérez", "ana@example.com", "123");

	        em.getTransaction().begin();
	        em.persist(usuario);
	        em.getTransaction().commit();

	        System.out.println("Usuario guardado: " + usuario);

	        em.close();
	        JPAUtil.cerrar();
	}

}
