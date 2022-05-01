package com.chensy.socialnetwork.dao.impl;

import com.chensy.socialnetwork.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@JdbcTest
class RoleDaoImplTest {

    public static final String ROLE_USER = "USER";
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    void getRoleByName() {
        RoleDaoImpl roleDao = new RoleDaoImpl(namedParameterJdbcTemplate);
        Role role_user = roleDao.getRoleByName(ROLE_USER);
        assertNotNull(role_user);
        assertEquals(ROLE_USER, role_user.getAuthority());
    }
}