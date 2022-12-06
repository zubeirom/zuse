package com.htwsaar.zuse;

import com.htwsaar.zuse.model.Article;
import com.htwsaar.zuse.model.Category;
import com.htwsaar.zuse.model.Warehouse;
import com.htwsaar.zuse.repository.*;
import com.htwsaar.zuse.util.JPAUtil;
import com.htwsaar.zuse.util.Util;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ArticleTest {
    private final IArticleRepository articleRepository = new ArticleRepository();
    private final IWarehouseRepository warehouseRepository = new WarehouseRepository();
    private final ICategoryRepository categoryRepository = new CategoryRepository();

    @AfterAll
    public static void cleanUp() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.createNativeQuery("DELETE FROM article").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM category").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM warehouse").executeUpdate();
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    public void testCreate() {
        Article article = createDummy("Nike");
        Assertions.assertTrue(article.getId() > 0);
    }

    @Test
    public void testUpdate() {
        Article article = createDummy("Nike");
        String newArticleName = "Adidas";
        article.setArticleName(newArticleName);
        articleRepository.update(article);
        Article updated = articleRepository.getOne(article.getId());
        Assertions.assertEquals(newArticleName, updated.getArticleName());
    }

    @Test
    public void testGetOne() {
        Article article = createDummy("Adidas");
        Article fetched = articleRepository.getOne(article.getId());
        Assertions.assertEquals(article.getId(), fetched.getId());
    }

    @Test
    public void testGetAll() {
        List<Article> articles =  articleRepository.getAll();
        Assertions.assertFalse(articles.isEmpty());
    }

    @Test
    public void testDelete() {
        Article article = createDummy("Deleting");
        articleRepository.delete(article.getId());
        Article deleted = articleRepository.getOne(article.getId());
        Assertions.assertNull(deleted);
    }

    private Article createDummy(String articleName) {
        Warehouse warehouse = warehouseRepository.create(new Warehouse("Zalando"));
        Category category = categoryRepository.create(new Category(Util.generateRandomString()));
        return articleRepository.create(new Article(warehouse, articleName, "White Sneakers", category, 109.99, 25));
    }
}
