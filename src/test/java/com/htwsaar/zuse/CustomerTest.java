package com.htwsaar.zuse;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.htwsaar.zuse.model.Customer;
import com.htwsaar.zuse.repository.ICustomerRepository;
import com.htwsaar.zuse.util.JPAUtil;
import com.htwsaar.zuse.repository.CustomerRepository;
import java.util.List;

public class CustomerTest {

	private final ICustomerRepository customerRepository = new CustomerRepository();

	/**
	 * Cleans up the DB after all Tests are finished
	 */
    @AfterAll
    public static void cleanUp() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.createNativeQuery("DELETE FROM customer").executeUpdate();
        entityTransaction.commit();
        entityManager.close();
    }
    
    /**
     * Creates a Customer Object and adds it to the DB
     * then checks if the customer creation was successful 
     * by checking if the Id exists
     */
    @Test
    public void testCreate() {
    	Customer newCustomer = customerRepository.create(sampleCustomer());
    	Assertions.assertTrue(newCustomer.getId()>0);
    }
    
    /**
	 * Creates a list of Customers which are in customerRepository
	 * and checks if the list is not Empty
	 */
    @Test
    public void testGetAll() {
    	List<Customer> customers = customerRepository.getAll();
    	Assertions.assertFalse(customers.isEmpty());
    	
    }

    /**
	 * Creates a new Customer and changes the name of it
	 * then checks if the Customer object contains the new name
	 */
    @Test
    public void testUpdate() {
    	String lastname = "Müller";
    	Customer newCustomer = customerRepository.create(sampleCustomer());
    	newCustomer.setLastName(lastname);
    	customerRepository.update(newCustomer);
    	Customer getUpdated = customerRepository.getOne(newCustomer.getId());
    	Assertions.assertEquals(newCustomer.getLastName(), getUpdated.getLastName());
    }
    
    /**
     * Creates a new customer object and adds it to the DB then
     * takes it from the DB and checks if the Id is not Null
     */
    @Test
    public void testGetOne() {
    	Customer newCustomer = customerRepository.create(sampleCustomer());
    	Customer customer = customerRepository.getOne(newCustomer.getId());
    	Assertions.assertNotNull(customer.getCompanyName());
    	}
    
    /**
     * Creates a new customer object adds it to the DB
     * and then deletes it then takes is from the DB and 
     * checks if the Object is Null
     */
    @Test
    public void testDelete() {
    	Customer newCustomer = customerRepository.create(sampleCustomer());
    	customerRepository.delete(newCustomer.getId());
    	Customer customer = customerRepository.getOne(newCustomer.getId());
    	Assertions.assertNull(customer);
    }

	private Customer sampleCustomer() {
		return new Customer("TerraX", "Max",
				"Mustermann", "Mustermail@muster.com", "+496666666");
	}
}
