package com.jigume.domain.goods.repository;

import com.jigume.domain.goods.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findCategoryByName(String categoryName);

    Optional<Category> findCategoryById(Long categoryId);
}