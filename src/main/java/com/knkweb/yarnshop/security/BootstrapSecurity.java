package com.knkweb.yarnshop.security;

import com.knkweb.yarnshop.domain.Authority;
import com.knkweb.yarnshop.domain.User;
import com.knkweb.yarnshop.repositories.AuthorityRepository;
import org.springframework.boot.CommandLineRunner;

public class BootstrapSecurity implements CommandLineRunner {
    private final AuthorityRepository authorityRepository;

    public BootstrapSecurity(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//
//        authorityRepository.saveAndFlush(Authority.builder().user(User.builder().username(
//                "manager").password("passA").build()).roll("ADMIN").build());
//        authorityRepository.saveAndFlush(Authority.builder().user(User.builder().username(
//                "sai").password("pass").build()).roll("CUSTOMER").build());
    }
}
