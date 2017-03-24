package com.joao.teste;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.joao.Entidade.EstudanteEntity;

public class EstudanteEntityTeste {

	private static EntityManager entityManager = null;
	
	/**
	 * Método que criar uma conexão com a persistence-unit teste-unit
	 * @return
	 */
	public static EntityManager getEntityManager(){
		
		if (entityManager == null){
			final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("teste-unit");
			entityManager = entityManagerFactory.createEntityManager();
		}
		
		return entityManager;
		
	}
	
	/**
	 * Método que popula o banco de dados com usuários válidos
	 */
	public static void populaBanco(){
		
		getEntityManager().createNativeQuery("INSERT INTO Estudante VALUES('1', 'João')").executeUpdate();
		getEntityManager().createNativeQuery("INSERT INTO Estudante VALUES('2', 'Felipe')").executeUpdate();
		getEntityManager().createNativeQuery("INSERT INTO Estudante VALUES('3', 'Rodrigues')").executeUpdate();
		
	}
	
	/**
	 * Método que vai iniciar a conexão com o banco de dados em memória antes do início dos testes.
	 */
    @BeforeClass
    public static void init(){
    	getEntityManager().getTransaction().begin();
    	populaBanco();
    }
	
	@Before
	public void antes(){
		
	}
	
	/**
	 * Após a execução de todos os teste  a conexão será fechada.
	 */
	@AfterClass
	public static void end(){
		getEntityManager().close();
	}
	
	/**
	 * Teste que valida se um estudando foi mesmo inserido na base
	 */
	@Test
	public void inseriNovoEstudante(){
		
		getEntityManager().createNativeQuery("INSERT INTO Estudante VALUES('4', 'João Assis')").executeUpdate();
		
		EstudanteEntity estudante = (EstudanteEntity) getEntityManager().createQuery("select e from EstudanteEntity e where e.nome = :nome").setParameter("nome", "João Assis").getSingleResult();
		
		assertEquals("João Assis", estudante.getNome());
		
	}
	
	@Test
	public void teste01(){
		

		List<EstudanteEntity> estudantes = new ArrayList<EstudanteEntity>();
		estudantes = (List<EstudanteEntity>) this.getEntityManager().createQuery("select e from EstudanteEntity e").getResultList();

		for (EstudanteEntity e : estudantes){
			System.out.println("ID: " + e.getId() + " " + e.getNome());
		}
		
		assertEquals("João", estudantes.get(0).getNome());
		
	}
	
}
