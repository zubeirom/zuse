package com.htwsaar.zuse;

import com.htwsaar.zuse.model.Address;
import com.htwsaar.zuse.model.Customer;
import com.htwsaar.zuse.repository.AddressRepository;
import com.htwsaar.zuse.repository.CustomerRepository;
import com.htwsaar.zuse.repository.IAddressRepository;
import com.htwsaar.zuse.repository.ICustomerRepository;
import com.htwsaar.zuse.util.JPAUtil;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Timestamp;
import java.util.List;

public class AddressTest {

	private final IAddressRepository addressRepository = new AddressRepository();
	private final ICustomerRepository customerRepository = new CustomerRepository();


	/**
	 * Cleans up the DB after all Tests are finished
	 */
	@AfterAll
	public static void cleanUp() {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.createNativeQuery("DELETE FROM address").executeUpdate();
		entityManager.createNativeQuery("DELETE FROM customer").executeUpdate();
		entityTransaction.commit();
		entityManager.close();
	}

	/**
	 * Create an Address Object and add it to the DB
	 * then check if the address creation was successfully by
	 * checking the if the ID exists
	 */
	@Test
	public void testCreate() {
		Customer c = customerRepository.create(new Customer("TerraX", "Max",
				"Mustermann", "Mustermail@muster.com", "+496666666"));
		Address newAddress = addressRepository.create(new Address(c,
				"StreetOne 21", "676767", "SimCity",
				"Germany"));
		Assertions.assertTrue(newAddress.getId() > 0);
	}

	/**
	 * Create a list of Addresses which are in addressRepository
	 * and check if the lists is not Empty
	 */
	@Test
	public void testGetAll() {
		List<Address> addresses = addressRepository.getAll();
		Assertions.assertFalse(addresses.isEmpty());
	}

	/**
	 * Creates a new Address and change the street of this address
	 * then get a new address object with the same ID and check if it
	 * Contains the new Street name
	 */
	@Test
	public void testUpdate() {
		Customer c = customerRepository.create(new Customer("TerraX", "Max",
				"Mustermann", "Mustermail@muster.com", "+496666666"));
		String newStreet = "HellStreetAlpha 666";
		Address newAddress = addressRepository.create(new Address(c,
				"HellStreet 666", "666666", "HellCity",
				"Hell"));
		newAddress.setStreet(newStreet);
		addressRepository.update(newAddress);
		Address getUpdated = addressRepository.getOne(newAddress.getId());
		Assertions.assertEquals(newStreet, getUpdated.getStreet());
	}

	/**
	 * Create a new Address Object and add it to the DB then take the address
	 * from the DB and check if the Id is not Null
	 */
	@Test
	public void testGetOne() {
		Customer c = customerRepository.create(new Customer("TerraX", "Max",
				"Mustermann", "Mustermail@muster.com", "+496666666"));
		Address address1 = new Address(c,
				"JumpStreet 98", "123456", "JumpCity",
				"Texas");
		Address newAddress = addressRepository.create(address1);
		Address address = addressRepository.getOne(newAddress.getId());
		Assertions.assertNotNull(addressRepository.getOne(address.getId()));
	}

	/**
	 * Creates an new Address object add it to the DB then delete it
	 * then take the Object again from the DB and check if the Object is
	 * Null
	 */
	@Test
	public void testDelete() {
		Customer c = customerRepository.create(new Customer("TerraX", "Max",
				"Mustermann", "Mustermail@muster.com", "+496666666"));
		Address newAddress = addressRepository.create(new Address(c,
				"MarieStraße 21", "654321", "MarieRose",
				"Marianna"));
		addressRepository.delete(newAddress.getId());
		Address address1 = addressRepository.getOne(newAddress.getId());
		Assertions.assertNull(address1);
	}


}
