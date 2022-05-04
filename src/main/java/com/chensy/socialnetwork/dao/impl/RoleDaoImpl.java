package com.chensy.socialnetwork.dao.impl;

import com.chensy.socialnetwork.dao.RoleDao;
import com.chensy.socialnetwork.mapper.RoleMapper;
import com.chensy.socialnetwork.model.Role;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RoleDaoImpl implements RoleDao {


    public static final String SQL_SELECT_ROLE_BY_NAME = "select id, name from role where name=:name";
    private static final String SQL_CREATE_NEW_USER_ROLE = "INSERT INTO user_role (user_id, role_id) " +
            "SELECT :user_id, r.id FROM role r WHERE r.name =:role_name";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RoleDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Role getRoleByName(String name) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", name);

        return namedParameterJdbcTemplate.query(SQL_SELECT_ROLE_BY_NAME, parameters, new RoleMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public void setRole(Long userId, String roleName) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", userId);
        parameters.addValue("role_name", roleName);

        namedParameterJdbcTemplate.update(SQL_CREATE_NEW_USER_ROLE, parameters);

    }


}
