package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Category;
import com.htwsaar.zuse.repository.CategoryRepository;
import com.htwsaar.zuse.repository.ICategoryRepository;

import java.util.List;

/**
 * Service to interact with an Article DB Object in your UI. 
 * The Service provides every action that is supported
 */
public class CategoryService implements ICategoryService {

	/**
	 * Repository of the Service
	 */
	private final ICategoryRepository categoryRepository;

	/**
	 * Creates a new CategoryService. The appropriate repository is created within
	 */
	public CategoryService() {
		this.categoryRepository = new CategoryRepository();
	}

	/**
	 * Creates a new ArticleService with a given Repository
	 * @param categoryRepository repository of service
	 */
	public CategoryService(ICategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	/**
	 * Creates a new Category in the database
	 * 
	 * @param category Category to create in database
	 * @return the created Category
	 */
	@Override
	public Category create(Category category) {
		return this.categoryRepository.create(category);
	}

	/**
	 * Updates a given Category in the database
	 * 
	 * @param category Category to update in database
	 */
	@Override
	public void update(Category category) {
		this.categoryRepository.update(category);
	}

	/**
	 * Deletes a Category of the database
	 * 
	 * @param categoryId id of the Category to delete
	 */
	@Override
	public void delete(int categoryId) {
		this.categoryRepository.delete(categoryId);
	}

	/**
	 * Gets one Category from the database
	 * 
	 * @param categoryId id of Category to get
	 * @return Category which was retrieved
	 */
	@Override
	public Category getOne(int categoryId) {
		return this.categoryRepository.getOne(categoryId);
	}

	/**
	 * Gets all Categories in the database
	 * 
	 * @return all Categories in the database
	 */
	@Override
	public List<Category> getAll() {
		return this.categoryRepository.getAll();
	}

	/**
	 * Finds a Category 
	 * 
	 * @param name name of the Category
	 * @return Category within the name
	 */
	@Override
	public Category findByName(String name) {
		return this.categoryRepository.findByName(name);
	}
}
