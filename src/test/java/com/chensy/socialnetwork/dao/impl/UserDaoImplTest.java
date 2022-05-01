package com.chensy.socialnetwork.dao.impl;

import com.chensy.socialnetwork.dao.UserDao;
import com.chensy.socialnetwork.dto.UserDTO;
import com.chensy.socialnetwork.model.Gender;
import com.chensy.socialnetwork.model.Role;
import com.chensy.socialnetwork.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@JdbcTest
class UserDaoImplTest {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    void findUserByEmail() {
        UserDaoImpl userDao = new UserDaoImpl(namedParameterJdbcTemplate);
        User userByEmail = userDao.findUserByEmail("ben@mail.ru");
        assertNotNull(userByEmail);
    }

    @Test
    void getUserById() {
        UserDaoImpl userDao = new UserDaoImpl(namedParameterJdbcTemplate);
        User userById = userDao.getUserById(1L);
        assertNotNull(userById);
    }

    @Test
    void updateUserSettings() {
        UserDaoImpl userDao = new UserDaoImpl(namedParameterJdbcTemplate);
        int i = userDao.updateUserSettings("qq", "qq", LocalDate.now(), "M", "123", 1L);
        assertTrue(i > 0);
    }

    @Test
    void makeUserAdmin() {
        UserDaoImpl userDao = new UserDaoImpl(namedParameterJdbcTemplate);
        User user = userDao.makeUserAdminById(1L);
        assertNotNull(user);
    }

    @Test
    void createUser() {
        UserDaoImpl userDao = new UserDaoImpl(namedParameterJdbcTemplate);
        User user = userDao.createUser(new User()
                .setEmail("123")
                .setFirstName("111")
                .setLastName("111")
                .setSex(Gender.FEMALE)
                .setPassword("111")
                .setDob(LocalDate.now()));
        assertNotNull(user);
    }

    @Test
    void blockUser() {
        UserDaoImpl userDao = new UserDaoImpl(namedParameterJdbcTemplate);
        User user = userDao.blockUserById(1L);
        assertNotNull(user);
    }

    @Test
    void getUserRole() {
        UserDaoImpl userDao = new UserDaoImpl(namedParameterJdbcTemplate);
        Role userRole = userDao.getUserRole(1L);
        assertNotNull(userRole);
    }
}