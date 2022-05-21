package com.chensy.socialnetwork.mapper;

import com.chensy.socialnetwork.model.Country;
import com.chensy.socialnetwork.model.Gender;
import com.chensy.socialnetwork.model.Role;
import com.chensy.socialnetwork.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User()
                .setEmail(rs.getString("username"))
                .setId(rs.getLong("user_id"))
                .setPassword(rs.getString("password"))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"))
                .setDob(rs.getObject("date_of_birth", LocalDate.class))
                .setSex(Gender.getGenderByLetter(rs.getString("sex")))
                .setPhone(rs.getString("phone"))
                .setInterests(rs.getString("interests"))
                .setCountry(getCountry(rs.getInt("country_id"), rs.getString("country_name")))
                .setRoles(getRole(rs.getString("role_name")));
    }

    private Country getCountry(Integer countryId, String countryName) {
        return new Country().setId(countryId).setName(countryName);
    }

    private Set<Role> getRole(String role_name) {
        Role role = Optional.ofNullable(role_name)
                .map(Role::valueOf)
                .orElse(null);

        if (role == null)
            return new HashSet<>();

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }
}
