package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Commission;
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

public class CommissionRepository implements ICommissionRepository {
    /**
     * Creates a new entry in the DB table
     * @param commission the Object to add to the DB
     * @return the given object which was added to the DB
     */
    @Override
    public Commission create(Commission commission) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        entityManager.persist(commission);

        entityTransaction.commit();
        entityManager.close();
        return commission;
    }
    /**
     * @return a List of all given object of the address table
     */
    @Override
    public List<Commission> getAll() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        List<Commission> commissions = entityManager.createNativeQuery("SELECT * FROM commission", Commission.class).getResultList();

        entityTransaction.commit();
        entityManager.close();

        return commissions;
    }
    /**
     * Serch in the DB for an object
     * @param commissionId ID of the needed Object
     * @return the Object with the given ID
     */
    @Override
    public Commission getOne(int commissionId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Commission commission = entityManager.find(Commission.class, commissionId);

        entityTransaction.commit();
        entityManager.close();

        return commission;
    }
    /**
     * Update an existing entry in the DB table
     * @param commission the Changed object
     */
    @Override
    public Commission update(Commission commission) {
        commission.setUpdatedAt(Util.getCurrentTimestamp());
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        entityManager.merge(commission);

        entityTransaction.commit();
        entityManager.close();

        return commission;
    }
    /**
     * Delete the DB entry with the given ID
     * @param commissionId Entry to delete
     */
    @Override
    public void delete(int commissionId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Commission commission = entityManager.find(Commission.class, commissionId);

        entityManager.remove(commission);

        entityTransaction.commit();
        entityManager.close();
    }
}
