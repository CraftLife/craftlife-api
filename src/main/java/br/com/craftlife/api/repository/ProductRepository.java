package br.com.craftlife.api.repository;

import br.com.craftlife.api.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface  ProductRepository extends JpaRepository<Product, Long> {

}
