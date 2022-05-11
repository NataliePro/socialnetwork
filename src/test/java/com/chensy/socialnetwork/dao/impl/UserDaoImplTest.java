package com.chensy.socialnetwork.dao.impl;


import com.chensy.socialnetwork.model.Gender;
import com.chensy.socialnetwork.model.Role;
import com.chensy.socialnetwork.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserDaoImplTest {

    @Autowired
    private UserDaoImpl userDao;

    @Test
    void findUserByEmail() {
        User userByEmail = userDao.findUserByEmail("ben@mail.ru");
        assertNotNull(userByEmail);
    }

    @Test
    void getUserById() {
        User userById = userDao.getUserById(1L);
        assertNotNull(userById);
    }

    @Test
    void updateUserSettings() {
        int i = userDao.updateUserSettings("qq", "qq", LocalDate.now(), "M", "123", 2L);
        assertTrue(i > 0);
    }

    @Test
    void makeUserAdmin() {
        User user = userDao.makeUserAdminById(1L);
        assertNotNull(user);
    }

    @Test
    void createUser() {
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
        User user = userDao.blockUserById(2L);
        assertNotNull(user);
    }

    @Test
    void getUserRole() {
        Role userRole = userDao.getUserRole(1L);
        assertNotNull(userRole);
    }
}