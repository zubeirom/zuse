package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Article;
import com.htwsaar.zuse.repository.ArticleRepository;
import com.htwsaar.zuse.repository.IArticleRepository;

import java.util.List;

/**
 * Service to interact with an Article DB object in your UI.
 * The Service provides every action that is supported
 */
public class ArticleService implements IArticleService {

	/**
	 * Repository of the Service
	 */
	private final IArticleRepository articleRepository;

	/**
	 * Creates a new ArticleService. The appropriate repository is created within
	 */
	public ArticleService() {
		articleRepository = new ArticleRepository();
	}

	/**
	 * Creates a new ArticleService with a given Repository
	 * @param articleRepository repository of service
	 */
	public ArticleService(IArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	/**
	 * Creates a new Article in the database
	 * 
	 * @param article Article to create in database
	 * @return the created Article
	 */
	@Override
	public Article create(Article article) {
		return this.articleRepository.create(article);
	}

	/**
	 * Updates a given Article in the database
	 * 
	 * @param article Article to update in database
	 */
	@Override
	public void update(Article article) {
		this.articleRepository.update(article);
	}

	/**
	 * Deletes an Article of the database
	 * 
	 * @param articleId id of the Article to delete
	 */
	@Override
	public void delete(int articleId) {
		this.articleRepository.delete(articleId);
	}

	/**
	 * Gets one Article from the database
	 * 
	 * @param articleId id of Article to get
	 * @return Article which was retrieved
	 */
	@Override
	public Article getOne(int articleId) {
		return this.articleRepository.getOne(articleId);
	}

	/**
	 * Gets all Articles in the database
	 * 
	 * @return all Articles in the database
	 */
	@Override
	public List<Article> getAll() {
		return this.articleRepository.getAll();
	}
	
	/**
	 * Finds all Articles in a Category 
	 * 
	 * @param categoryId id of the Category
	 * @return all Articles within the Category  
	 */
	@Override
	public List<Article> findByCategory(int categoryId) {
		return this.articleRepository.findByCategory(categoryId);
	}

	/**
	 * Finds all Articles within a Warehouse and Category
	 * 
	 * @param warehouseId id of the Warehouse
	 * @param categoryId id of the Category
	 * @return all Articles within the Warehouse and Category 
	 */
	@Override
	public List<Article> findByWarehouseAndCategory(int warehouseId, int categoryId) {
		return this.articleRepository.findByWarehouseAndCategory(warehouseId, categoryId);
	}
}
