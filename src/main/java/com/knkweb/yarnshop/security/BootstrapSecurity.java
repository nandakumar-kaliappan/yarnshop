package com.knkweb.yarnshop.security;

import com.knkweb.yarnshop.domain.Authority;
import com.knkweb.yarnshop.domain.User;
import com.knkweb.yarnshop.repositories.AuthorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapSecurity implements CommandLineRunner {
    private final AuthorityRepository authorityRepository;

    public BootstrapSecurity(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(authorityRepository.findAll().size() == 0){
            bootstrapData();
        }
    }

    private void bootstrapData() {
        authorityRepository.saveAndFlush(Authority.builder().user(User.builder().username(
                "manager").password("passM").build()).roll("MANAGER").build());
        authorityRepository.saveAndFlush(Authority.builder().user(User.builder().username(
                "sai").password("passS").build()).roll("CUSTOMER").build());
        authorityRepository.saveAndFlush(Authority.builder().user(User.builder().username(
                "admin").password("passA").build()).roll("ADMIN").build());
    }
}
