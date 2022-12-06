package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Category;

import java.util.List;

public interface ICategoryRepository {
    Category create(Category category);
    List<Category> getAll();
    Category getOne(int categoryId);
    void update(Category category);
    void delete(int categoryId);
    Category findByName(String name);
}
