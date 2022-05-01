package com.chensy.socialnetwork.mapper;

import com.chensy.socialnetwork.model.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        String roleName = rs.getString("name");
        return Role.valueOf(roleName);
    }
}
