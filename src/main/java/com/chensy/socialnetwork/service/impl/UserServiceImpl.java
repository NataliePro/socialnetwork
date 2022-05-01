package com.chensy.socialnetwork.service.impl;

import com.chensy.socialnetwork.converters.UserDtoToUserConverter;
import com.chensy.socialnetwork.converters.UserToUserDtoConverter;
import com.chensy.socialnetwork.dao.UserDao;
import com.chensy.socialnetwork.dto.UserDTO;
import com.chensy.socialnetwork.model.User;
import com.chensy.socialnetwork.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserToUserDtoConverter userToUserDtoConverter;
    private final UserDtoToUserConverter userDtoToUserConverter;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao,
                           UserToUserDtoConverter userToUserDtoConverter,
                           UserDtoToUserConverter userDtoToUserConverter,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void makeUserAdmin(long userId) {
        userDao.makeUserAdminById(userId);
    }

    @Override
    public void blockUser(long userId) {
        userDao.blockUserById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User userByEmail = userDao.findUserByEmail(email);
        return userToUserDtoConverter.convert(userByEmail);
    }

    @Override
    public void createUser(UserDTO userDto) {
        userDao.createUser(userDtoToUserConverter.convert(userDto));
    }

    @Override
    public void updateUser(UserDTO userDto) {
        userDao.updateUser(userDtoToUserConverter.convert(userDto));
    }

    @Override
    public void updatePassword(String password, Long id) {
        String encodedPassword = passwordEncoder.encode(password);
        userDao.updatePassword(encodedPassword, id);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User userById = userDao.getUserById(id);
        return userToUserDtoConverter.convert(userById);
    }

    @Override
    public List<UserDTO> getUserByFirstNameAndLastNamePrefix(String firstPrefix, String lastPrefix) {
        List<User> userByPrefix = userDao.getUsersByFirstNameAndLastNamePrefix(firstPrefix.concat("%"), lastPrefix.concat("%"));
        return userByPrefix.stream()
                .map(user -> userToUserDtoConverter.convert(user))
                .collect(Collectors.toList());
    }

}
