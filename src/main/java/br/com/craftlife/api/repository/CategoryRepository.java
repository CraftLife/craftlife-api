package br.com.craftlife.api.repository;

import br.com.craftlife.api.domain.Category;
import br.com.craftlife.api.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.active and c.parentCategory is null ORDER BY c.displayOrder")
    List<Category> findAllRootCategoriesOrderedByDisplayOrder();

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products p WHERE c.active AND p.active AND c.id = :id ORDER BY p.displayOrder")
    Optional<Category> findCategoryWithProductOrderedByDisplayOrder(@Param("id") Long categoryId);

}
