package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Address;
import com.htwsaar.zuse.util.JPAUtil;
import com.htwsaar.zuse.util.Util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

/**
 * Interface to connect the DB with the Java objects and save the data or delete
 * it. Manage the DB access and guarantees the persistence of the data when new
 * data are added to the DB or deleted from the DB
 */

public class AddressRepository implements IAddressRepository {

	/**
	 * Creates a new entry in the DB table
	 *
	 * @param address the Object to add to the DB
	 * @return the given object which was added to the DB
	 */
	@Override
	public Address create(Address address) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(address);
		entityTransaction.commit();
		entityManager.close();
		return address;
	}

	/**
	 * @return a List of all given object of the address table
	 */
	@Override
	public List<Address> getAll() {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Address> addresses = entityManager.createNativeQuery("SELECT * FROM address", Address.class)
				.getResultList();
		entityTransaction.commit();
		entityManager.close();
		return addresses;
	}

	/**
	 * Search in the DB for an object
	 *
	 * @param addressId ID of the needed Object
	 * @return the Object with the given ID
	 */
	@Override
	public Address getOne(int addressId) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Address address = entityManager.find(Address.class, addressId);
		entityTransaction.commit();
		entityManager.close();
		return address;
	}

	/**
	 * Update an existing entry in the DB table
	 *
	 * @param address the Changed object
	 */
	@Override
	public void update(Address address) {
		address.setUpdatedAt(Util.getCurrentTimestamp());
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.merge(address);
		entityTransaction.commit();
		entityManager.close();
	}

	/**
	 * Delete the DB entry with the given ID
	 *
	 * @param addressId Entry to delete
	 */
	@Override
	public void delete(int addressId) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Address address = entityManager.find(Address.class, addressId);
		entityManager.remove(address);
		entityTransaction.commit();
		entityManager.close();
	}

	/**
	 * Finds all Addresses of a Customer
	 *
	 * @param customerId ID of the customer
	 * @return a list of all Addresses
	 */
	@Override
	public List<Address> findByCustomer(int customerId) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Address> addresses = entityManager
				.createNativeQuery("SELECT * FROM address where customerId = " + customerId, Address.class)
				.getResultList();
		entityTransaction.commit();
		entityManager.close();
		return addresses.isEmpty() ? null : addresses;
	}

}
