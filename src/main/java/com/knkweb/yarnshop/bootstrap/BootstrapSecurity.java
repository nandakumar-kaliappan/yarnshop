package com.knkweb.yarnshop.bootstrap;

import com.knkweb.yarnshop.domain.Authority;
import com.knkweb.yarnshop.domain.User;
,
import com.knkweb.yarnshop.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BootstrapSecurity implements CommandLineRunner {
    private final UserRepository userRepository;

    public BootstrapSecurity(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    }
}
