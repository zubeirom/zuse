package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Order;
import com.htwsaar.zuse.util.JPAUtil;
import com.htwsaar.zuse.util.Util;
import org.hibernate.boot.JaccPermissionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
/**
 * Interface to connect the DB with the Java objects and save the data or delete it.
 * Manage the DB access and guarantees the persistence of the data when new data are added to the
 * DB or deleted from the DB
 */

public class OrderRepository implements IOrderRepository {
    /**
     * Creates a new entry in the DB table
     * @param order the Object to add to the DB
     * @return the given object which was added to the DB
     */
    @Override
    public Order create(Order order) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(order);
        entityTransaction.commit();
        entityManager.close();
        return order;
    }
    /**
     * @return a List of all given object of the address table
     */
    @Override
    public List<Order> getAll() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        List<Order> orders = entityManager.createNativeQuery("SELECT * FROM `order`", Order.class).getResultList();
        entityTransaction.commit();
        entityManager.close();
        return orders;
    }
    /**
     * Serch in the DB for an object
     * @param orderId ID of the needed Object
     * @return the Object with the given ID
     */
    @Override
    public Order getOne(int orderId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        Order order = entityManager.find(Order.class, orderId);
        entityTransaction.commit();
        entityManager.close();
        return order;
    }
    /**
     * Update an existing entry in the DB table
     * @param order the Changed object
     */
    @Override
    public void update(Order order) {
        order.setUpdatedAt(Util.getCurrentTimestamp());
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.merge(order);
        entityTransaction.commit();
        entityManager.close();
    }
    /**
     * Delete the DB entry with the given ID
     * @param orderId Entry to delete
     */
    @Override
    public void delete(int orderId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        Order order = entityManager.find(Order.class, orderId);
        entityManager.remove(order);
        entityTransaction.commit();
        entityManager.close();
    }
}
