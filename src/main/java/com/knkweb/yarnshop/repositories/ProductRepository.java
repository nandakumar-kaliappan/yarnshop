package com.knkweb.yarnshop.repositories;



import com.knkweb.yarnshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByDescription(String description);
}
