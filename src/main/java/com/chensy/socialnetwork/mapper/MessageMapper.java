package com.chensy.socialnetwork.mapper;

import com.chensy.socialnetwork.model.Gender;
import com.chensy.socialnetwork.model.Message;
import com.chensy.socialnetwork.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Message()
                .setId(rs.getInt("id"))
                .setTime(rs.getObject("time", LocalDateTime.class))
                .setSender(getUserSender(rs))
                .setReceiver(getUserReceiver(rs))
                .setMessage(rs.getString("message"));
    }

    private User getUserSender(ResultSet rs) throws SQLException {
        return getUser(rs, "us_username", "us_id", "us_password", "us_first_name", "us_last_name",
                "us_date_of_birth", "us_sex", "us_phone", "us_interests");
    }

    private User getUserReceiver(ResultSet rs) throws SQLException {
        return getUser(rs, "ur_username", "ur_id", "ur_password", "ur_first_name", "ur_last_name",
                "ur_date_of_birth", "ur_sex", "ur_phone", "ur_interests");
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
                         String interests) throws SQLException {
        return new User()
                .setEmail(rs.getString(username))
                .setId(rs.getLong(id))
                .setPassword(rs.getString(pass))
                .setFirstName(rs.getString(fname))
                .setLastName(rs.getString(lname))
                .setDob(rs.getObject(dob, LocalDate.class))
                .setSex(Gender.getGenderByLetter(rs.getString(sex)))
                .setPhone(rs.getString(phone))
                .setInterests(rs.getString(interests));
    }
}
