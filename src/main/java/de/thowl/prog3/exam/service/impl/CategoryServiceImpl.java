package de.thowl.prog3.exam.service.impl;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void saveCategory(String categoryName, Long userId) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setUserId(userId);
        categoryRepository.save(category);
    }

    public Category getCategory(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    public List<Category> getCategoriesByUser(Long userId) {
        return categoryRepository.findByUserId(userId);
    }
}
