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

    public String saveCategory(String categoryName, Long userId, String categoryColour) {
        if (getCategory(categoryName, userId) == null) {
            Category category = new Category();
            category.setCategoryName(categoryName);
            category.setUserId(userId);
            category.setCategoryColour(categoryColour);
            categoryRepository.save(category);
            return "Category created";
        } else {
            return "Category already exists";
        }
    }

    public Category getCategory(String categoryName, Long userId) {
        return categoryRepository.findByCategoryNameAndUserId(categoryName, userId);
    }

    public List<Category> getCategoriesByUser(Long userId) {
        return categoryRepository.findByUserId(userId);
    }
}
