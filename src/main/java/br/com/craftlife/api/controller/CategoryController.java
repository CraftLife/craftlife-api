package br.com.craftlife.api.controller;

import br.com.craftlife.api.controller.dto.CategoryResponse;
import br.com.craftlife.api.controller.dto.CategoryTreeResponse;
import br.com.craftlife.api.domain.Category;
import br.com.craftlife.api.mapper.CategoryMapper;
import br.com.craftlife.api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;


@RequestMapping("category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @GetMapping()
    public ResponseEntity<List<CategoryTreeResponse>> getCategories() {
        List<Category> categories = categoryRepository.findAllRootCategoriesOrderedByDisplayOrder();

        if (categories.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(categoryMapper.categoryListToCategoryTreeResponseList(categories));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long categoryId) {
        Category category = categoryRepository.findCategoryWithProductOrderedByDisplayOrder(categoryId).orElseThrow(() -> new NoSuchElementException("Element not found!"));

        return ResponseEntity.ok(categoryMapper.categoryToCategoryResponse(category));
    }
}
