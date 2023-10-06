package com.knkweb.yarnshop.bootstrap;

import com.knkweb.yarnshop.domain.*;

import com.knkweb.yarnshop.repositories.CategoryRepository;
import com.knkweb.yarnshop.repositories.CustomerRepository;
import com.knkweb.yarnshop.repositories.ProductRepository;
import com.knkweb.yarnshop.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class BootstrapSecurity implements CommandLineRunner {
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public BootstrapSecurity(CustomerRepository customerRepository,
                             CategoryRepository categoryRepository,
                             ProductRepository productRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if(customerRepository.findAll().size() == 0){
            bootstrapData();
        }
    }
    private void bootstrapData() {
        User usera = User.builder().username("admin").password("admin").build();
        usera.addAuthorities(Authority.builder().role("ADMIN").build());

        User userm = User.builder().username("manager").password("manager").build();
        userm.addAuthorities(Authority.builder().role("ADMIN").build());
        userm.addAuthorities(Authority.builder().role("MANAGER").build());

        User users = User.builder().username("saitextiles").password("user").build();
        users.addAuthorities(Authority.builder().role("CUSTOMER").build());

        User userak = User.builder().username("akrgroups").password("user").build();
        userak.addAuthorities(Authority.builder().role("CUSTOMER").build());

        User uservp = User.builder().username("vpandco").password("user").build();
        uservp.addAuthorities(Authority.builder().role("CUSTOMER").build());
        
        customerRepository.saveAndFlush(Customer.builder().customerName("VP & Co")

                .address(Address.builder().address("Tiruppur").build())
                .email("vp@gmail.com")
                .phone("6800799150")
                .build());

        customerRepository.saveAndFlush(Customer.builder().customerName("Sai Textile")
                .user(users)
                .address(Address.builder().address("NBS, Tiruppur").build())
                .email("sai@gmail.com")
                .phone("9600799150")
                .build());

        customerRepository.saveAndFlush(Customer.builder().customerName("AKR Groups")
                .user(userak)
                .address(Address.builder().address("PN rd, Tiruppur").build())
                .email("akr@gmail.com")
                .phone("8100799150")
                .build());


        userRepository.saveAndFlush(usera);
        userRepository.saveAndFlush(userm);

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
