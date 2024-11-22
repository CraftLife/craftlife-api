package br.com.craftlife.api.controller;

import br.com.craftlife.api.controller.dto.ProductResponse;
import br.com.craftlife.api.domain.Product;
import br.com.craftlife.api.mapper.ProductMapper;
import br.com.craftlife.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@RequestMapping("product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found!"));

        return ResponseEntity.ok(productMapper.productToProductResponse(product));
    }

    @PostMapping("/update-summary/{productId}")
    @Secured({"DIRECTOR"})
    public ResponseEntity<ProductResponse> updateProductSummary(@PathVariable Long productId, @RequestBody String summary) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found!"));

        product.setSummary(summary);
        product = productRepository.save(product);
        return ResponseEntity.ok(productMapper.productToProductResponse(product));
    }

}
