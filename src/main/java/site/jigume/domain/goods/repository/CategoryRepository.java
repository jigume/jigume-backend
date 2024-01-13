package site.jigume.domain.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.jigume.domain.goods.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findCategoryByName(String categoryName);

    Optional<Category> findCategoryById(Long categoryId);
}