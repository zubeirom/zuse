package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Category;

import java.util.List;

public interface ICategoryService {
    Category create(Category category);
    void update(Category category);
    void delete(int categoryId);
    Category getOne(int categoryId);
    List<Category> getAll();
    Category findByName(String name);
}
