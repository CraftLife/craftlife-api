package br.com.craftlife.api.repository;

import br.com.craftlife.api.domain.Category;
import br.com.craftlife.api.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products p WHERE c.active AND p.active ORDER BY c.displayOrder, p.displayOrder")
//    List<Category> findAllCategoriesOrderedByDisplayOrder();
//
//    @Query("SELECT c FROM Category c WHERE c.active ORDER BY c.displayOrder")
//    List<Category> findCategoriesTree();
}
