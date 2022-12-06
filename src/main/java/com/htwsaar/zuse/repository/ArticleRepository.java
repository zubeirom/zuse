package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Article;
import com.htwsaar.zuse.util.JPAUtil;
import com.htwsaar.zuse.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * Interface to connect the DB with the Java objects and save the data or delete
 * it. Manage the DB access and guarantees the persistence of the data when new
 * data are added to the DB or deleted from the DB
 */


public class ArticleRepository implements IArticleRepository {
	/**
	 * Creates a new entry in the DB table
	 *
	 * @param article the Object to add to the DB
	 * @return the given object which was added to the DB
	 */
	@Override
	public Article create(Article article) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(article);
		entityTransaction.commit();
		entityManager.close();
		return article;
	}

	/**
	 * @return a List of all given object of the address table
	 */
	@Override
	public List<Article> getAll() {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Article> articles = entityManager.createNativeQuery("SELECT * FROM article", Article.class)
				.getResultList();
		entityTransaction.commit();
		entityManager.close();
		return articles;
	}

	/**
	 * Search in the DB for an object
	 *
	 * @param articleId ID of the needed Object
	 * @return the Object with the given ID
	 */
	@Override
	public Article getOne(int articleId) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Article article = entityManager.find(Article.class, articleId);
		entityTransaction.commit();
		entityManager.close();
		return article;
	}

	/**
	 * Update an existing entry in the DB table
	 *
	 * @param article the Changed object
	 */
	@Override
	public void update(Article article) {
		article.setUpdatedAt(Util.getCurrentTimestamp());
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.merge(article);
		entityTransaction.commit();
		entityManager.close();
	}

	/**
	 * Delete the DB entry with the given ID
	 *
	 * @param articleId Entry to delete
	 */
	@Override
	public void delete(int articleId) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Article article = entityManager.find(Article.class, articleId);
		entityManager.remove(article);
		entityTransaction.commit();
		entityManager.close();
	}

	/**
	 * Finds all Articles within the given Category
	 *
	 * @param categoryId id of the Category to find Articles in 
	 * @return the Articles within the given Category
	 */
	@Override
	public List<Article> findByCategory(int categoryId) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Article> articles = entityManager
				.createNativeQuery("SELECT * FROM article where categoryId = " + categoryId, Article.class)
				.getResultList();
		entityTransaction.commit();
		entityManager.close();
		return articles.isEmpty() ? null : articles;
	}

	/**
	 * Finds all Articles within the given Warehouse and Category
	 *
	 * @param warehouseId id of the Warehouse to find Articles in
	 * @param categoryId id of the Category to find Articles in
	 * @return the Articles within the given warehouse and category
	 */
	@Override
	public List<Article> findByWarehouseAndCategory(int warehouseId, int categoryId) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Article> articles = entityManager.createNativeQuery(
				"SELECT * FROM article where warehouseId = " + warehouseId + " and categoryId = " + categoryId,
				Article.class).getResultList();
		entityTransaction.commit();
		entityManager.close();
		return articles.isEmpty() ? null : articles;
	}
}
