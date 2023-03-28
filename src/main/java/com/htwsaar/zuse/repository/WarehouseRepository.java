package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Warehouse;
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

public class WarehouseRepository implements IWarehouseRepository {
    /**
     * Creates a new entry in the DB table
     * @param warehouse the Object to add to the DB
     * @return the given object which was added to the DB
     */
    @Override
    public Warehouse create(Warehouse warehouse) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(warehouse);
        entityTransaction.commit();
        entityManager.close();
        return warehouse;
    }
    /**
     * @return a List of all given object of the address table
     */
    @Override
    public List<Warehouse> getAll() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        List<Warehouse> warehouses = entityManager.createNativeQuery("SELECT * FROM warehouse", Warehouse.class).getResultList();
        entityTransaction.commit();
        entityManager.close();
        return warehouses;
    }
    /**
     * Serch in the DB for an object
     * @param warehouseId ID of the needed Object
     * @return the Object with the given ID
     */
    @Override
    public Warehouse getOne(int warehouseId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        Warehouse warehouse = entityManager.find(Warehouse.class, warehouseId);
        entityTransaction.commit();
        entityManager.close();
        return warehouse;
    }
    /**
     * Update an existing entry in the DB table
     * @param warehouse the Changed object
     */
    @Override
    public void update(Warehouse warehouse) {
        warehouse.setUpdatedAt(Util.getCurrentTimestamp());
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.merge(warehouse);
        entityTransaction.commit();
        entityManager.close();
    }
    /**
     * Delete the DB entry with the given ID
     * @param warehouseId Entry to delete
     */
    @Override
    public void delete(int warehouseId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        Warehouse warehouse = entityManager.find(Warehouse.class, warehouseId);
        entityManager.remove(warehouse);
        entityTransaction.commit();
        entityManager.close();
    }
}
