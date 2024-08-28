package br.com.craftlife.api.controller;

import br.com.craftlife.api.controller.dto.PageableResponse;
import br.com.craftlife.api.domain.Category;
import br.com.craftlife.api.domain.Product;
import br.com.craftlife.api.domain.PunishBan;
import br.com.craftlife.api.repository.BanRepository;
import br.com.craftlife.api.repository.CategoryRepository;
import br.com.craftlife.api.repository.ProductRepository;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;


@RequestMapping("product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found!"));

        return ResponseEntity.ok(product);
    }
}
