package com.knkweb.yarnshop.repositories;

import com.knkweb.yarnshop.domain.Authority;
import com.knkweb.yarnshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    public Authority findByUserAndRoll(User user, String roll);
    public Authority findByUser(User user);

}
