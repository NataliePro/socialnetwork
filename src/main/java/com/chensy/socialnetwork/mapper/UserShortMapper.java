package com.chensy.socialnetwork.mapper;


import com.chensy.socialnetwork.model.Country;
import com.chensy.socialnetwork.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserShortMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User()
                .setEmail(rs.getString("username"))
                .setId(rs.getLong("user_id"))
                .setFirstName(rs.getString("first_name"))
                .setCountry(getCountry(rs.getInt("country_id"), rs.getString("country_name")))
                .setLastName(rs.getString("last_name"));
    }
    private Country getCountry(Integer countryId, String countryName) {
        return new Country().setId(countryId).setName(countryName);
    }
}
