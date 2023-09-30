package com.knkweb.yarnshop;

import com.knkweb.yarnshop.domain.Authority;
import com.knkweb.yarnshop.domain.User;
import com.knkweb.yarnshop.repositories.AuthorityRepository;
import com.knkweb.yarnshop.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ComponentScan({"com.knkweb.yarnshop.security"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EntitiyInDatabaseIntegrationTest {
    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    UserRepository userRepository;

    String password = "passwordSample";
    String user1Name = "TestUser1";
    String user2Name = "TestUser2";
    String role1 ="Admin";
    String role2 = "Customer";

    @BeforeEach
    void setUp() {
        authorityRepository.saveAndFlush(Authority.builder().user(User.builder().username(
                user1Name).password(password).build()).role(role1).build());
        authorityRepository.saveAndFlush(Authority.builder().user(User.builder().username(
                user2Name).password(password).build()).role(role2).build());
    }

    @Test
    void testAuthorityRepositoryForWrite() {
        int count = authorityRepository.findAll().size();
        assertEquals(count, 5);
    }

    @Test
    void testUserRepositoryForWrite(){
        int count = userRepository.findAll().size();
        assertEquals(count, 5);
    }

    @Test
    void testUser(){
        Optional<User> userOptional = userRepository.findByUsername(user1Name);
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        assertEquals(user.getUsername(), user1Name);
        assertEquals(user.getPassword(), password);
    }
    @Test
    void testAuthorityByName(){
        Optional<Authority> authority =
                authorityRepository.findByUser(userRepository.findByUsername(user1Name).get());
        assertEquals(authority.get().getRole(), role1);

    }
    @Test
    void testAuthorityByNameForBootstrapData(){
        Optional<Authority> authority =
                authorityRepository.findByUser(userRepository.findByUsername("manager").get());
        assertEquals(authority.get().getRole(), "MANAGER");

        Optional<Authority> authority1 =
                authorityRepository.findByUser(userRepository.findByUsername("admin").get());
        assertEquals(authority1.get().getRole(), "ADMIN");

        Optional<Authority> authority2 =
                authorityRepository.findByUser(userRepository.findByUsername("sai").get());
        assertEquals(authority2.get().getRole(), "CUSTOMER");


    }

    @Test
    void testUserByNameForBootstrapData(){
        User user =
                userRepository.findByUsername("manager").get();
        assertEquals(user.getPassword(), "passM");

        User user1 =
                userRepository.findByUsername("admin").get();
        assertEquals(user1.getPassword(), "passA");

        User user2 =
                userRepository.findByUsername("sai").get();
        assertEquals(user2.getPassword(), "passS");
    }
}
