
package com.htwsaar.zuse.repository;


import com.htwsaar.zuse.model.OrderItem;
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

public class OrderItemRepository implements IOrderItemRepository {
	/**
	 * Creates a new entry in the DB table
	 * @param orderItem the Object to add to the DB
	 * @return the given object which was added to the DB
	 */
	@Override
	public OrderItem create(OrderItem orderItem) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(orderItem);
		entityTransaction.commit();
		entityManager.close();
		return orderItem;
	}
	/**
	 * @return a List of all given object of the address table
	 */
	@Override
	public List<OrderItem> getAll() {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<OrderItem> orderItems = entityManager.createNativeQuery("SELECT * FROM orderItem", OrderItem.class).getResultList();
		entityTransaction.commit();
		entityManager.close();
		return orderItems;
	}
	/**
	 * Search in the DB for an object
	 * @param OrderItemId ID of the needed Object
	 * @return the Object with the given ID
	 */
	@Override
	public OrderItem getOne(int OrderItemId) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		OrderItem orderItem = entityManager.find(OrderItem.class, OrderItemId);
		entityTransaction.commit();
		entityManager.close();
		return orderItem;
	}
	/**
	 * Update an existing entry in the DB table
	 * @param orderItem the Changed object
	 */
	@Override
	public void update(OrderItem orderItem) {
		orderItem.setUpdatedAt(Util.getCurrentTimestamp());
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.merge(orderItem);
		entityTransaction.commit();
		entityManager.close();
	}
	/**
	 * Delete the DB entry with the given ID
	 * @param OrderItemId Entry to delete
	 */
	@Override
	public void delete(int OrderItemId) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		OrderItem orderItem = entityManager.find(OrderItem.class, OrderItemId);
		entityManager.remove(orderItem);
		entityTransaction.commit();
		entityManager.close();

	}

	/**
	 * Finds all OrderItems within a given order
	 *
	 * @param orderId id of the order to find OrderItems in
	 * @return the OrderItems within the given order
	 */
	@Override
	public List<OrderItem> findByOrder(int orderId) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<OrderItem> orderItems = entityManager.createNativeQuery("SELECT * FROM orderItem where orderId = " + orderId, OrderItem.class).getResultList();
		entityTransaction.commit();
		entityManager.close();
		return orderItems.isEmpty() ? null : orderItems;

	}
}