package com.knkweb.yarnshop.security.config;

import com.knkweb.yarnshop.domain.Category;
import com.knkweb.yarnshop.domain.Product;
import com.knkweb.yarnshop.repositories.CategoryRepository;
import com.knkweb.yarnshop.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductAndCategoryIT {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void testCategorySave() {
        Category white = Category.builder().description("white").build();
        Category blue = Category.builder().description("blue").build();
        categoryRepository.saveAllAndFlush(List.of(white, blue));
        List<Category> categoryIterator = categoryRepository.findAll();
        assertEquals(categoryIterator.size(), 2);
        assertNotNull(categoryIterator.get(0).getId());
        assertNotNull(categoryRepository.findByDescription("white").get());
        assertEquals(categoryRepository.findByDescription("white").get().getDescription(),"white");
    }
    @Test
    void testProductSave() {
        List<Product> products = new ArrayList<>();
        for(int i=100; i<130; i++){
            String productName = "W"+i;
            products.add(Product.builder().description(productName).colour(productName).build());
        }

        productRepository.saveAllAndFlush(products);
        List<Product> products1 = productRepository.findAll();
        assertEquals(products1.size(), 30);
        assertNotNull(products1.get(1).getId());
    }
    @Test
    @Transactional
    void testProductWithCategorySave() {
        Category white = Category.builder().description("white").build();
        Category blue = Category.builder().description("blue").build();
        categoryRepository.saveAllAndFlush(List.of(white, blue));
        List<Product> products = new ArrayList<>();
        for(int i=100; i<130; i++){
            String productName = "W"+i;
            Product prod = Product.builder().description(productName).colour(productName).build();
            Category categoryWhite =categoryRepository.findByDescription("white").get();
//            categoryWhite.getProducts().add(prod);
            prod.addCategory(categoryWhite);
            productRepository.saveAndFlush(prod);
        }


        List<Product> products1 = productRepository.findAll();
        assertEquals(products1.size(), 30);
        assertNotNull(products1.get(1).getId());
        assertEquals(products1.get(1).getCategories().iterator().next().getDescription(),"white");

        Category white1 = categoryRepository.findByDescription("white").get();
        assertEquals(white1.getProducts().size(), 30);

    }
}
