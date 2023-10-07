package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    public String findMaxRole(UserDetails userDetails);

    User findUser(UserDetails userDetails);
}
