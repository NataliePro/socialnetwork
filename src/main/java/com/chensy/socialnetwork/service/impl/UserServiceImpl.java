package com.chensy.socialnetwork.service.impl;

import com.chensy.socialnetwork.converters.UserDtoToUserConverter;
import com.chensy.socialnetwork.converters.UserToUserDtoConverter;
import com.chensy.socialnetwork.dao.RoleDao;
import com.chensy.socialnetwork.dao.UserDao;
import com.chensy.socialnetwork.dto.UserDTO;
import com.chensy.socialnetwork.model.Role;
import com.chensy.socialnetwork.model.User;
import com.chensy.socialnetwork.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final UserToUserDtoConverter userToUserDtoConverter;
    private final UserDtoToUserConverter userDtoToUserConverter;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao,
                           RoleDao roleDao,
                           UserToUserDtoConverter userToUserDtoConverter,
                           UserDtoToUserConverter userDtoToUserConverter,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
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

    @Transactional
    @Override
    public void createUser(UserDTO userDto) {
        User user = userDtoToUserConverter.convert(userDto);
        User userWithId = userDao.createUser(user);
        roleDao.setRole(userWithId.getId(), Role.ROLE_USER.name());
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
                .map(userToUserDtoConverter::convert)
                .collect(Collectors.toList());
    }

}
