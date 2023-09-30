package com.knkweb.yarnshop.repositories;

import com.knkweb.yarnshop.domain.Authority;
import com.knkweb.yarnshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsernameAndPassword(String username, String password);
    public  Optional<User> findByUsername(String username);
}
