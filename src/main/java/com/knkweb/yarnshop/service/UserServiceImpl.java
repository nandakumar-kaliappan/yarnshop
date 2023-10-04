package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.domain.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
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
}
