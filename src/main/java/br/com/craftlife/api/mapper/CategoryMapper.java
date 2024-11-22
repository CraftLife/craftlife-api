package br.com.craftlife.api.mapper;

import br.com.craftlife.api.controller.dto.CategoryResponse;
import br.com.craftlife.api.controller.dto.CategoryTreeResponse;
import br.com.craftlife.api.domain.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse categoryToCategoryResponse(Category category);

    List<CategoryTreeResponse> categoryListToCategoryTreeResponseList(List<Category> categories);

}
