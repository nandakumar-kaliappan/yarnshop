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

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
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
        assertEquals(count, 2);
    }

    @Test
    void testUserRepositoryForWrite(){
        int count = userRepository.findAll().size();
        assertEquals(count, 2);
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
}