package com.chensy.socialnetwork.converters;

import com.chensy.socialnetwork.dto.UserDTO;
import com.chensy.socialnetwork.model.Gender;
import com.chensy.socialnetwork.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoToUserConverter implements Converter<UserDTO, User> {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User convert(UserDTO userDTO) {
        return new User().setId(userDTO.getId())
                .setEmail(userDTO.getEmail())
                .setFirstName(userDTO.getFirstName())
                .setLastName(userDTO.getLastName())
                .setPhone(userDTO.getPhone())
                .setSex(Gender.valueOf(userDTO.getSex()))
                .setDob(userDTO.getDob())
                .setPassword(passwordEncoder.encode(userDTO.getPassword()));
    }
}
