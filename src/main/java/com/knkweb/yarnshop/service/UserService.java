package com.knkweb.yarnshop.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    public String findMaxRole(UserDetails userDetails);
}
