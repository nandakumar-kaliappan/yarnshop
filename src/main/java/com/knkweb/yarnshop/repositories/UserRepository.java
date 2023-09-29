package com.knkweb.yarnshop.repositories;

import com.knkweb.yarnshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsernameAndPassword(String username, String password);
    public  User findByUsername(String username);
}
