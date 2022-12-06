package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Article;

import java.util.List;

public interface IArticleRepository {
	Article create(Article article);

	List<Article> getAll();

	Article getOne(int articleId);

	void update(Article article);

	void delete(int articleId);

	List<Article> findByCategory(int categoryId);

	List<Article> findByWarehouseAndCategory(int warehouseId, int categoryId);
}
