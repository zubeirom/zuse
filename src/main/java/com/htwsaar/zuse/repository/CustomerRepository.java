package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Customer;
import com.htwsaar.zuse.util.JPAUtil;
import com.htwsaar.zuse.util.Util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
/**
 * Interface to connect the DB with the Java objects and save the data or delete it.
 * Manage the DB access and guarantees the persistence of the data when new data are added to the
 * DB or deleted from the DB
 */

public class CustomerRepository implements ICustomerRepository {
	/**
	 * Creates a new entry in the DB table
	 * @param customer the Object to add to the DB
	 * @return the given object which was added to the DB
	 */
	@Override
	public Customer create(Customer customer) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		entityManager.persist(customer);

		entityTransaction.commit();
		entityManager.close();
		return customer;
	}
	/**
	 * @return a List of all given object of the address table
	 */
	@Override
	public List<Customer> getAll() {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		List<Customer> customers = entityManager.createNativeQuery("SELECT * FROM customer", Customer.class).getResultList();

		entityTransaction.commit();
		entityManager.close();

		return customers;
	}
	/**
	 * Serch in the DB for an object
	 * @param customerId ID of the needed Object
	 * @return the Object with the given ID
	 */
	@Override
	public Customer getOne(int customerId) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		Customer customer = entityManager.find(Customer.class, customerId);

		entityTransaction.commit();
		entityManager.close();

		return customer;
	}
	/**
	 * Update an existing entry in the DB table
	 * @param customer the Changed object
	 */
	@Override
	public Customer update(Customer customer) {
		customer.setUpdatedAt(Util.getCurrentTimestamp());
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		entityManager.merge(customer);

		entityTransaction.commit();
		entityManager.close();

		return customer;
	}
	/**
	 * Delete the DB entry with the given ID
	 * @param customerId Entry to delete
	 */
	@Override
	public void delete(int customerId) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		Customer customer = entityManager.find(Customer.class, customerId);
		entityManager.remove(customer);

		entityTransaction.commit();
		entityManager.close();
	}
}
