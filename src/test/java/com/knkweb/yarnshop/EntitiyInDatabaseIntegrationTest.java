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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    String roll1 ="Admin";
    String roll2 = "Customer";

    @BeforeEach
    void setUp() {
        authorityRepository.saveAndFlush(Authority.builder().user(User.builder().username(
                user1Name).password(password).build()).roll(roll1).build());
        authorityRepository.saveAndFlush(Authority.builder().user(User.builder().username(
                user2Name).password(password).build()).roll(roll2).build());
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
        User user = userRepository.findByUsername(user1Name);
        assertEquals(user.getUsername(), user1Name);
        assertEquals(user.getPassword(), password);
    }
    @Test
    void testAuthorityByName(){
        Authority authority =
                authorityRepository.findByUser(userRepository.findByUsername(user1Name));
        assertEquals(authority.getRoll(), roll1);

    }
    @Test
    void testAuthorityByNameForBootstrapData(){
        Authority authority =
                authorityRepository.findByUser(userRepository.findByUsername("manager"));
        assertEquals(authority.getRoll(), "MANAGER");

        Authority authority1 =
                authorityRepository.findByUser(userRepository.findByUsername("admin"));
        assertEquals(authority1.getRoll(), "ADMIN");

        Authority authority2 =
                authorityRepository.findByUser(userRepository.findByUsername("sai"));
        assertEquals(authority2.getRoll(), "CUSTOMER");
    }

    @Test
    void testUserByNameForBootstrapData(){
        User user =
                userRepository.findByUsername("manager");
        assertEquals(user.getPassword(), "passM");

        User user1 =
                userRepository.findByUsername("admin");
        assertEquals(user1.getPassword(), "passA");

        User user2 =
                userRepository.findByUsername("sai");
        assertEquals(user2.getPassword(), "passS");
    }
}
