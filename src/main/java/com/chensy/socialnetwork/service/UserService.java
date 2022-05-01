package com.chensy.socialnetwork.service;

import com.chensy.socialnetwork.dto.UserDTO;
import com.chensy.socialnetwork.model.User;

import java.util.List;

public interface UserService {
    void makeUserAdmin(long userId);
    void blockUser(long userId);
    List<User> getAllUsers();
    UserDTO getUserByEmail(String email);
    void createUser(UserDTO userDto);

    void updateUser(UserDTO user);

    void updatePassword(String password, Long id);

    UserDTO getUserById(Long id);
    List<UserDTO> getUserByFirstNameAndLastNamePrefix(String firstPrefix, String lastPrefix);
}
