package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Article;

import java.util.List;

public interface IArticleService {
	Article create(Article article);

	void update(Article article);

	void delete(int articleId);

	Article getOne(int articleId);

	List<Article> getAll();
	
	List<Article> findByCategory(int categoryId);

	List<Article> findByWarehouseAndCategory(int warehouseId, int categoryId);
}
