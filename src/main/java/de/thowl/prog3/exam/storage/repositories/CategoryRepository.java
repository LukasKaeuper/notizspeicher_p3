package de.thowl.prog3.exam.storage.repositories;

import de.thowl.prog3.exam.storage.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    public Category findByCategoryName(String categoryName);

    public List<Category> findByUserId(Long userId);
}
