package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.domain.User;
import com.knkweb.yarnshop.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public String findMaxRole(UserDetails userDetails) {//todo: improvement needed
        for(GrantedAuthority authority: userDetails.getAuthorities()){
            if(authority.getAuthority().equals("ADMIN")){
                return "admin";
            }
        }
        for(GrantedAuthority authority: userDetails.getAuthorities()){
            if(authority.getAuthority().equals("MANAGER")){
                return "admin";
            }
        }
        for(GrantedAuthority authority: userDetails.getAuthorities()){
            if(authority.getAuthority().equals("CUSTOMER")){
                return "customer";
            }
        }
        return "all";
    }

    @Override
    public User findUser(UserDetails userDetails) {
        String userName = userDetails.getUsername();
        Optional<User> userOptional = userRepository.findByUsername(userName);
        return userOptional.orElse(null);
    }
}
