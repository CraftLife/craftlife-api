package br.com.craftlife.api.controller;

import br.com.craftlife.api.domain.Category;
import br.com.craftlife.api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping()
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> category = categoryRepository.findAllCategoriesOrderedByDisplayOrder();

        if (category.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(category);
    }
}
