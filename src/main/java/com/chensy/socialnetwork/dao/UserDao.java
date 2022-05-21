package com.chensy.socialnetwork.dao;

import com.chensy.socialnetwork.model.Role;
import com.chensy.socialnetwork.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserDao {

    User findUserByEmail(String email);
    User getUserById(Long userId);
    List<User> getUsersByFirstNameAndLastNamePrefix(String firstPrefix, String lastPrefix);
    User makeUserAdminById(Long userId);
    User blockUserById(Long userId);
    List<User> getRecentUsers(int maxUsersCount);
    int updateUserSettings(String firstName, String lastName,
                           LocalDate dob, String sex, String phone,
                           Long id, String country, String interests);
    int updatePassword(String password, Long id);
    User createUser(User user);
    void updateUser(User user);
    Role getUserRole(Long userId);
}
