package br.com.craftlife.api.mapper;

import br.com.craftlife.api.controller.dto.ProductResponse;
import br.com.craftlife.api.domain.Product;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse productToProductResponse(Product product);

}
