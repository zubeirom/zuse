package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Category;
import com.htwsaar.zuse.util.JPAUtil;
import com.htwsaar.zuse.util.Util;
import org.hibernate.Criteria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
/**
 * Interface to connect the DB with the Java objects and save the data or delete it.
 * Manage the DB access and guarantees the persistence of the data when new data are added to the
 * DB or deleted from the DB
 */

public class CategoryRepository implements ICategoryRepository {
    /**
     * Creates a new entry in the DB table
     * @param category the Object to add to the DB
     * @return the given object which was added to the DB
     */
    @Override
    public Category create(Category category) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(category);
        entityTransaction.commit();
        entityManager.close();
        return category;
    }
    /**
     * @return a List of all given object of the address table
     */
    @Override
    public List<Category> getAll() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        List<Category> categorys = entityManager.createNativeQuery("SELECT * FROM category", Category.class).getResultList();
        entityTransaction.commit();
        entityManager.close();
        return categorys;
    }
    /**
     * Serch in the DB for an object
     * @param categoryId ID of the needed Object
     * @return the Object with the given ID
     */
    @Override
    public Category getOne(int categoryId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        Category category = entityManager.find(Category.class, categoryId);
        entityTransaction.commit();
        entityManager.close();
        return category;
    }
    /**
     * Update an existing entry in the DB table
     * @param category the Changed object
     */
    @Override
    public void update(Category category) {
        category.setUpdatedAt(Util.getCurrentTimestamp());
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.merge(category);
        entityTransaction.commit();
        entityManager.close();
    }
    /**
     * Delete the DB entry with the given ID
     * @param categoryId Entry to delete
     */
    @Override
    public void delete(int categoryId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        Category category = entityManager.find(Category.class, categoryId);
        entityManager.remove(category);
        entityTransaction.commit();
        entityManager.close();
    }

    /**
     * Serch for a Category by a given String
     * @param name the String to serch
     * @return the first match with the given String
     */

    @Override
    public Category findByName(String name) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        List<Category> categories = entityManager.createNativeQuery("SELECT * FROM category where name = '" + name + "'", Category.class).getResultList();
        entityTransaction.commit();
        entityManager.close();
        return categories.isEmpty() ? null : categories.get(0);
    }
}
