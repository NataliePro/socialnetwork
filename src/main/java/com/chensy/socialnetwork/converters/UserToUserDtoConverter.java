package com.chensy.socialnetwork.converters;


import com.chensy.socialnetwork.dto.UserDTO;
import com.chensy.socialnetwork.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.chensy.socialnetwork.Constants.ROLE_ADMIN;


@Component
public class UserToUserDtoConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User user) {
        if (user == null)
            return null;

        return new UserDTO().setId(user.getId())
                      .setEmail(user.getEmail())
                      .setPassword(user.getPassword())
                      .setFirstName(user.getFirstName())
                      .setLastName(user.getLastName())
                      .setPhone(user.getPhone())
                      .setSex(user.getSex().name())
                      .setIsAdmin(user.getRoles().stream().anyMatch(r -> ROLE_ADMIN.equals(r.getAuthority())))
                      .setDob(user.getDob());
    }
}
