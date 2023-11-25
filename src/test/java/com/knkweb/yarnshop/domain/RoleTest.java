package com.knkweb.yarnshop.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    Role role;
    @Test
    void testRoleToString(){
        role = Role.ROLE_ADMIN;
        assertEquals(role.toString(), "ROLE_ADMIN");
    }
}