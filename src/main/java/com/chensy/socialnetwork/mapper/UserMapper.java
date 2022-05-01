package com.chensy.socialnetwork.mapper;

import com.chensy.socialnetwork.model.Gender;
import com.chensy.socialnetwork.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User()
                .setEmail(rs.getString("username"))
                .setId(rs.getLong("id"))
                .setPassword(rs.getString("username"))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"))
                .setDob(rs.getObject("date_of_birth", LocalDate.class))
                .setSex(Gender.getGenderByLetter(rs.getString("sex")))
                .setPhone(rs.getString("phone"))
                .setInterests(rs.getString("interests"));
    }
}
