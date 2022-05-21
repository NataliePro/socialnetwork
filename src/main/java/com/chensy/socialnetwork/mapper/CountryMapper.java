package com.chensy.socialnetwork.mapper;

import com.chensy.socialnetwork.model.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Country()
                .setName(rs.getString("country_name"))
                .setId(rs.getInt("country_id"));
    }
}
