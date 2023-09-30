package com.knkweb.yarnshop.repositories;

import com.knkweb.yarnshop.domain.Authority;
import com.knkweb.yarnshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    public Optional<Authority> findByUserAndRole(User user, String role);
    public Optional<Authority> findByUser(User user);

}
