package org.example.springtube.repositories;

import org.example.springtube.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // You can add custom query methods here if needed
    Category findByName(String name);

    Category save(Category category);

    void deleteById(Long categoryId);
}
