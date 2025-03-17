package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.Category;

import java.util.List;

public interface CategoryService {

    Category getCategory(String categoryName);

    List<Category> getCategoriesByUser(Long userId);

    void saveCategory(String categoryName, Long userId);
}
