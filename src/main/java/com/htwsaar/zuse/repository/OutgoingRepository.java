package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Outgoing;
import com.htwsaar.zuse.util.JPAUtil;
import com.htwsaar.zuse.util.Util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
/**
 * Interface to connect the DB with the Java objects and save the data or delete it.
 * Manage the DB access and guarantees the persistence of the data when new data are added to the
 * DB or deleted from the DB
 */

public class OutgoingRepository implements IOutgoingRepository {
    /**
     * Creates a new entry in the DB table
     * @param outgoing the Object to add to the DB
     * @return the given object which was added to the DB
     */
    @Override
    public Outgoing create(Outgoing outgoing) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(outgoing);
        entityTransaction.commit();
        entityManager.close();
        return outgoing;
    }
    /**
     * @return a List of all given object of the address table
     */
    @Override
    public List<Outgoing> getAll() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        List<Outgoing> outgoingorders = entityManager.createNativeQuery("SELECT * FROM outgoing", Outgoing.class).getResultList();
        entityTransaction.commit();
        entityManager.close();
        return outgoingorders;
    }
    /**
     * Serch in the DB for an object
     * @param outgoingId ID of the needed Object
     * @return the Object with the given ID
     */
    @Override
    public Outgoing getOne(int outgoingId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        Outgoing outgoing = entityManager.find(Outgoing.class, outgoingId);
        entityTransaction.commit();
        entityManager.close();
        return outgoing;
    }
    /**
     * Update an existing entry in the DB table
     * @param outgoing the Changed object
     */
    @Override
    public void update(Outgoing outgoing) {
        outgoing.setUpdatedAt(Util.getCurrentTimestamp());
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.merge(outgoing);
        entityTransaction.commit();
        entityManager.close();
    }
    /**
     * Delete the DB entry with the given ID
     * @param outgoingId Entry to delete
     */
    @Override
    public void delete(int outgoingId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        Outgoing outgoing = entityManager.find(Outgoing.class, outgoingId);
        entityManager.remove(outgoing);
        entityTransaction.commit();
        entityManager.close();
    }
}
