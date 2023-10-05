package com.knkweb.yarnshop.bootstrap;

import com.knkweb.yarnshop.domain.Authority;
import com.knkweb.yarnshop.domain.Category;
import com.knkweb.yarnshop.domain.Product;
import com.knkweb.yarnshop.domain.User;

import com.knkweb.yarnshop.repositories.CategoryRepository;
import com.knkweb.yarnshop.repositories.ProductRepository;
import com.knkweb.yarnshop.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class BootstrapSecurity implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public BootstrapSecurity(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if(userRepository.findAll().size() == 0){
            bootstrapData();
        }
    }
    private void bootstrapData() {
        User usera = User.builder().username("admin").password("pa").build();
        usera.addAuthorities(Authority.builder().role("ADMIN").build());

        User userm = User.builder().username("manager").password("pm").build();
        userm.addAuthorities(Authority.builder().role("ADMIN").build());
        userm.addAuthorities(Authority.builder().role("MANAGER").build());

        User users = User.builder().username("sai").password("ps").build();
        users.addAuthorities(Authority.builder().role("CUSTOMER").build());
        
        userRepository.saveAndFlush(userm);
        userRepository.saveAndFlush(usera);
        userRepository.saveAndFlush(users);

        Category white = Category.builder().description("white").build();
        Category blue = Category.builder().description("blue").build();
        categoryRepository.saveAllAndFlush(List.of(white, blue));

        Category blueSaved = categoryRepository.findByDescription("blue").get();
        List<Product> products = new ArrayList<>();
        for(int i=100; i<130; i++){
            String productName = "B"+i;
            Product product =
                    Product.builder().colour(productName).description(productName).build();
            product.addCategory(blueSaved);
            products.add(product);
        }
        productRepository.saveAllAndFlush(products);

        Category whiteSaved = categoryRepository.findByDescription("white").get();
        products = new ArrayList<>();
        for(int i=100; i<130; i++){
            String productName = "W"+i;
            Product product =
                    Product.builder().colour(productName).description(productName).build();
            product.addCategory(whiteSaved);
            products.add(product);
        }
        productRepository.saveAllAndFlush(products);

    }

}
