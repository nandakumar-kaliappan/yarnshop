package com.knkweb.yarnshop.security.config;

import com.knkweb.yarnshop.domain.User;

import com.knkweb.yarnshop.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional  = userRepository.findByUsername(username);
        userOptional.orElseThrow(() -> new UsernameNotFoundException("Please Check the entered " +
                "username :("));
        return userOptional.map(MyUserDetails::new).get();
    }
}
