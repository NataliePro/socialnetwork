package com.chensy.socialnetwork.dao.impl;

import com.chensy.socialnetwork.dao.UserDao;
import com.chensy.socialnetwork.mapper.RoleMapper;
import com.chensy.socialnetwork.mapper.UserMapper;
import com.chensy.socialnetwork.mapper.UserShortMapper;
import com.chensy.socialnetwork.model.Country;
import com.chensy.socialnetwork.model.Gender;
import com.chensy.socialnetwork.model.Role;
import com.chensy.socialnetwork.model.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDaoImpl implements UserDao {

    public static final String SQL_SELECT_RECENT_USERS =
            "SELECT " +
            "u.id as user_id, " +
            "u.username, " +
            "u.password, " +
            "u.first_name, " +
            "u.last_name, " +
            "u.date_of_birth," +
            "u.sex," +
            "u.phone," +
            "u.interests,  " +
            "u.country as country_id,  " +
            "c.name as country_name  " +
            "FROM user u " +
            "LEFT JOIN countries c ON c.id = u.country " +
            "order by registered DESC LIMIT :maxUsersCount";

    public static final String SQL_SELECT_USER_BY_ID =
            "SELECT " +
            "u.id as user_id, " +
            "u.username, " +
            "u.password, " +
            "u.first_name, " +
            "u.last_name, " +
            "u.date_of_birth," +
            "u.sex," +
            "u.phone," +
            "u.interests,  " +
            "u.country as country_id,  " +
            "c.name as country_name,  " +
            "r.name as role_name  " +
            "FROM user u " +
            "LEFT JOIN user_role ur ON ur.user_id = u.id " +
            "LEFT JOIN role r ON r.id = ur.role_id " +
            "LEFT JOIN countries c ON c.id = u.country " +
            "WHERE u.id =:user_id";

    public static final String SQL_SELECT_USER_BY_PREFIX =
            "SELECT " +
            "u.id as user_id, " +
            "u.username, " +
            "u.password, " +
            "u.first_name, " +
            "u.last_name, " +
            "u.date_of_birth," +
            "u.sex," +
            "u.phone," +
            "u.interests,  " +
            "u.country as country_id,  " +
            "c.name as country_name  " +
            "FROM user u " +
            "LEFT JOIN countries c ON c.id = u.country " +
            "WHERE first_name LIKE :firstPrefix AND last_name LIKE :lastPrefix " +
            "order by u.id";

    public static final String SQL_SELECT_USER_BY_EMAIL =
            "SELECT u.id as user_id, " +
            "u.username, " +
            "u.password, " +
            "u.first_name, " +
            "u.last_name, " +
            "u.date_of_birth," +
            "u.sex," +
            "u.phone," +
            "u.interests,  " +
            "u.country as country_id,  " +
            "c.name as country_name,  " +
            "r.name as role_name  " +
            "FROM user u " +
            "LEFT JOIN user_role ur ON ur.user_id = u.id " +
            "LEFT JOIN role r ON r.id = ur.role_id " +
            "LEFT JOIN countries c ON c.id = u.country " +
            "WHERE username =:email";

    public static final String SQL_MAKE_USER_ADMIN_BY_ID = "UPDATE user_role ur " +
            "SET ur.role_id = (SELECT id FROM role WHERE name = 'ROLE_ADMIN') " +
            "WHERE ur.user_id =:user_id";
    private static final String SQL_BLOCK_USER_BY_ID = "DELETE FROM user_role " +
            "WHERE user_id =:user_id";
    private static final String SQL_CREATE_NEW_USER = "INSERT INTO user (username, password, first_name, last_name, date_of_birth, sex, phone, registered, country) " +
            "VALUES (:username, :password, :first_name, :last_name, :dob, :sex, :phone, current_date(), (SELECT id FROM countries WHERE name =:country ))";
    public static final String SQL_UPDATE_USER_BY_ID = "UPDATE user " +
            "SET first_name =:first_name," +
            "last_name =:last_name," +
            "phone =:phone," +
            "sex =:sex," +
            "date_of_birth =:dob, " +
            "interests =:interests, " +
            "country = (SELECT id FROM countries WHERE name =:country )" +
            "WHERE id =:user_id";

    public static final String SQL_UPDATE_PASSWORD_BY_USER_ID = "UPDATE user " +
            "SET password =:password " +
            "WHERE id =:user_id";

    public static final String SQL_GET_USER_ROLE = "SELECT name FROM role r " +
            "INNER JOIN user_role ur ON ur.role_id = r.id AND ur.user_id =:user_id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User findUserByEmail(String email) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("email", email);

        return getUserFromDb(SQL_SELECT_USER_BY_EMAIL, parameters);
    }

    @Override
    public User getUserById(Long userId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", userId);

        return getUserFromDb(SQL_SELECT_USER_BY_ID, parameters);
    }

    @Override
    public List<User> getUsersByFirstNameAndLastNamePrefix(String firstPrefix, String lastPrefix) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("firstPrefix", firstPrefix);
        parameters.addValue("lastPrefix", lastPrefix);

        return namedParameterJdbcTemplate.query(SQL_SELECT_USER_BY_PREFIX, parameters, new UserShortMapper());
    }

    private User getUserFromDb(String sql, MapSqlParameterSource parameters) {

        List<User> userList = namedParameterJdbcTemplate.query(sql, parameters, new UserMapper());
        if (userList != null && !userList.isEmpty()) {
            Set<Role> roles = userList.stream().flatMap(user -> user.getRoles().stream()).collect(Collectors.toSet());
            User user = userList.get(0);
            user.setRoles(roles);
            return user;
        }
        return null;
    }

    private int updateUser(String sql, MapSqlParameterSource parameters) {
        return namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    @Transactional
    public User makeUserAdminById(Long userId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", userId);
        int i = updateUser(SQL_MAKE_USER_ADMIN_BY_ID, parameters);
        if (i > 0) {
            return getUserById(userId);
        }
        return null;
    }

    @Override
    @Transactional
    public User blockUserById(Long userId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", userId);
        int i = updateUser(SQL_BLOCK_USER_BY_ID, parameters);
        if (i > 0) {
            return getUserById(userId);
        }
        return null;
    }

    @Override
    public List<User> getRecentUsers(int maxUsersCount) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("maxUsersCount", maxUsersCount);

        return namedParameterJdbcTemplate.query(SQL_SELECT_RECENT_USERS, parameters, new UserShortMapper());
    }

    @Override
    public int updateUserSettings(String firstName,
                                  String lastName,
                                  LocalDate dob,
                                  String sex,
                                  String phone,
                                  Long id,
                                  String countryName,
                                  String interests) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", id);
        parameters.addValue("sex", sex);
        parameters.addValue("first_name", firstName);
        parameters.addValue("last_name", lastName);
        parameters.addValue("phone", phone);
        parameters.addValue("dob", dob.toString());
        parameters.addValue("country", countryName);
        parameters.addValue("interests", interests);
        return updateUser(SQL_UPDATE_USER_BY_ID, parameters);
    }

    @Override
    public int updatePassword(String password, Long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", id);
        parameters.addValue("password", password);
        return updateUser(SQL_UPDATE_PASSWORD_BY_USER_ID, parameters);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("username", user.getEmail());
        parameters.addValue("password", user.getPassword());
        parameters.addValue("first_name", user.getFirstName());
        parameters.addValue("last_name", user.getLastName());
        parameters.addValue("phone", user.getPhone());
        parameters.addValue("dob", user.getDob().toString());
        parameters.addValue("sex", user.getSex().getGenderLetter());
        parameters.addValue("country", Optional.ofNullable(user.getCountry()).map(Country::getName).orElse(null));

        int i = updateUser(SQL_CREATE_NEW_USER, parameters);
        if (i > 0) {
            return findUserByEmail(user.getEmail());
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        updateUserSettings(user.getFirstName(),
                user.getLastName(),
                user.getDob(),
                getUserSex(user),
                user.getPhone(),
                user.getId(),
                Optional.ofNullable(user.getCountry()).map(Country::getName).orElse(null),
                user.getInterests());
    }

    @Override
    public Role getUserRole(Long userId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", userId);
        return namedParameterJdbcTemplate.query(SQL_GET_USER_ROLE, parameters, new RoleMapper())
                .stream().findFirst().orElse(null);
    }

    private String getUserSex(User user) {
        return Optional.ofNullable(user.getSex())
                .map(Gender::getGenderLetter)
                .orElse("?");
    }


}
