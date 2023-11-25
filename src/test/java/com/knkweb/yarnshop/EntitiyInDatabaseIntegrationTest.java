package com.knkweb.yarnshop;

import com.knkweb.yarnshop.domain.Authority;
import com.knkweb.yarnshop.domain.Role;
import com.knkweb.yarnshop.domain.User;

import com.knkweb.yarnshop.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan({"com.knkweb.yarnshop.security"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EntitiyInDatabaseIntegrationTest {


    @Autowired
    UserRepository userRepository;

    String password = "passwordSample";
    String user1Name = "TestUser1";
    String user2Name = "TestUser2";
    String userUnsaved = "TestUser3";
    Role role1 =Role.ROLE_ADMIN;
    Role role2 = Role.ROLE_CUSTOMER;

    @BeforeEach
    void setUp() {
//        authorityRepository.saveAndFlush(Authority.builder().user(User.builder().username(
//                user1Name).password(password).build()).role(role1).build());
//        authorityRepository.saveAndFlush(Authority.builder().user(User.builder().username(
//                user2Name).password(password).build()).role(role2).build());
    }



    @Test
    void testAddUserWithRole(){
        User user = User.builder().username(userUnsaved).password(password)
        .build();
        user.addAuthorities(Authority.builder().role(role1).build());
        user.addAuthorities(Authority.builder().role(role2).build());
        System.out.println("-------".repeat(5)+"2");
        userRepository.save(user);
        userRepository.flush();

        System.out.println("-------".repeat(5)+"3");
        User user2  = userRepository.findByUsername(userUnsaved).get();
        assertNotNull(user2);
        assertEquals(user2.getUsername(), userUnsaved);
        assertEquals(user2.getAuthorities().size(),2);


    }
}
