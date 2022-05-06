package com.chensy.socialnetwork.mapper;

import com.chensy.socialnetwork.model.Friendship;
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

public class FriendshipMapper implements RowMapper<Friendship> {
    @Override
    public Friendship mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Friendship()
                .setId(rs.getLong("id"))
                .setAccepted(rs.getInt("accepted") == 1 ? true : false)
                .setUserReceiver(getUserReceiver(rs))
                .setUserSender(getUserSender(rs));
    }

    private User getUserSender(ResultSet rs) throws SQLException {
        return getUser(rs, "us_username", "us_id", "us_password", "us_first_name", "us_last_name",
                "us_date_of_birth", "us_sex", "us_phone", "us_interests", "us_role_name");
    }

    private User getUserReceiver(ResultSet rs) throws SQLException {
        return getUser(rs, "ur_username", "ur_id", "ur_password", "ur_first_name", "ur_last_name",
                "ur_date_of_birth", "ur_sex", "ur_phone", "ur_interests", "ur_role_name");
    }

    private User getUser(ResultSet rs,
                         String username,
                         String id,
                         String pass,
                         String fname,
                         String lname,
                         String dob,
                         String sex,
                         String phone,
                         String interests,
                         String rolename) throws SQLException {
        return new User()
                .setEmail(rs.getString(username))
                .setId(rs.getLong(id))
                .setPassword(rs.getString(pass))
                .setFirstName(rs.getString(fname))
                .setLastName(rs.getString(lname))
                .setDob(rs.getObject(dob, LocalDate.class))
                .setSex(Gender.getGenderByLetter(rs.getString(sex)))
                .setPhone(rs.getString(phone))
                .setInterests(rs.getString(interests))
                .setRoles(getRole(rs.getString(rolename)));
    }

    private Set<Role> getRole(String role_name) {
        Role role = Optional.ofNullable(role_name)
                .map(Role::valueOf)
                .orElseThrow(() -> new InvalidParameterException(String.format("Role %s not found!", role_name)));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }
}
