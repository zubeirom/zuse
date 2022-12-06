package com.htwsaar.zuse;

import com.htwsaar.zuse.util.Util;
import org.junit.jupiter.api.*;

import com.htwsaar.zuse.model.Category;
import com.htwsaar.zuse.repository.*;
import com.htwsaar.zuse.util.JPAUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class CategoryTest {
	
    private final ICategoryRepository categoryRepository = new CategoryRepository();
    
	/**
	 * Cleans up the DB after all Tests are finished
	 */
    @AfterAll
    public static void cleanUp() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.createNativeQuery("DELETE FROM category").executeUpdate();
        entityTransaction.commit();
        entityManager.close();
    }
    
    /**
     * Create a Category Object and add it to the DB
     * then check if the category creation was successful by 
     * checking if the Id exists
     */
	@Test
	public void testCreate() {
		Category newcategory = categoryRepository.create(new Category (Util.generateRandomString()));
		Assertions.assertTrue(newcategory.getId()>0);
	}
	
	/**
	 * Creates a list of Categories which are in categoryRepository
	 * and checks if the list is not Empty
	 */
	@Test
	public void testGetAll() {
		 List<Category> categories = categoryRepository.getAll();
		 Assertions.assertFalse(categories.isEmpty());
	}
	
	/**
	 * Creates a new Category and changes the name of it
	 * then checks if the Category object contains the new name
	 */
    @Test
    public void testUpdate() {	
    	String newName = Util.generateRandomString();
    	Category newcategory = categoryRepository.create(new Category (Util.generateRandomString()));
    	newcategory.setName(newName);
    	categoryRepository.update(newcategory);
    	Category getUpdated = categoryRepository.getOne(newcategory.getId());
    	Assertions.assertEquals(newName, getUpdated.getName());
    }
    
    /**
     * Creates a new category object and adds it to the DB then
     * takes it from the DB and checks if the Id id not Null
     * 
     */
    @Test
    public void testGetOne() {
    	Category c1 = new Category (Util.generateRandomString());
    	Category newcategory = categoryRepository.create(c1);
    	Category category = categoryRepository.getOne(newcategory.getId());
    	Assertions.assertNotNull(category.getName());
    }
    
    /**
     * Creates a new Category Object adds it to the DB
     * and then deletes it then takes is from the DB and 
     * checks if the Object is Null
     */
    @Test
    public void testDelete() {
    	Category newcategory = categoryRepository.create(new Category (Util.generateRandomString()));
    	categoryRepository.delete(newcategory.getId());
    	Category c1 =categoryRepository.getOne(newcategory.getId());
    	Assertions.assertNull(c1);
    }
}
