package com.chensy.socialnetwork.dao.impl;

import com.chensy.socialnetwork.dao.MessageDao;
import com.chensy.socialnetwork.dao.UserDao;
import com.chensy.socialnetwork.mapper.FriendshipMapper;
import com.chensy.socialnetwork.mapper.MessageMapper;
import com.chensy.socialnetwork.model.Message;
import com.chensy.socialnetwork.model.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class MessageDaoImpl implements MessageDao {

    private static final String SQL_SELECT_ALL_RECENT_MESSAGE_BY_ID =
            "SELECT m.id as id, " +
            "m.time as time, " +
            "m.message as message, " +
            "ur.id as ur_id, " +
            "ur.username as ur_username, " +
            "ur.password as ur_password, " +
            "ur.first_name as ur_first_name, " +
            "ur.last_name as ur_last_name, " +
            "ur.date_of_birth as ur_date_of_birth, " +
            "ur.sex as ur_sex, " +
            "ur.phone as ur_phone, " +
            "ur.interests as ur_interests, " +
            "us.id as us_id, " +
            "us.username as us_username, " +
            "us.password as us_password, " +
            "us.first_name as us_first_name, " +
            "us.last_name as us_last_name, " +
            "us.date_of_birth as us_date_of_birth, " +
            "us.sex as us_sex, " +
            "us.phone as us_phone, " +
            "us.interests as us_interests " +
            "FROM messages m " +
            "LEFT JOIN user ur ON ur.id = m.receiver " +
            "LEFT JOIN user us ON us.id = m.sender " +
            "WHERE m.id IN (SELECT MAX(id) FROM messages WHERE sender =:id OR receiver =:id GROUP BY sender, receiver)  ";
    private static final String SQL_SELECT_CONVERSATION =
            "SELECT m.id as id, " +
            "m.time as time, " +
            "m.message as message, " +
            "ur.id as ur_id, " +
            "ur.username as ur_username, " +
            "ur.password as ur_password, " +
            "ur.first_name as ur_first_name, " +
            "ur.last_name as ur_last_name, " +
            "ur.date_of_birth as ur_date_of_birth, " +
            "ur.sex as ur_sex, " +
            "ur.phone as ur_phone, " +
            "ur.interests as ur_interests, " +
            "us.id as us_id, " +
            "us.username as us_username, " +
            "us.password as us_password, " +
            "us.first_name as us_first_name, " +
            "us.last_name as us_last_name, " +
            "us.date_of_birth as us_date_of_birth, " +
            "us.sex as us_sex, " +
            "us.phone as us_phone, " +
            "us.interests as us_interests " +
            "FROM messages m " +
            "LEFT JOIN user ur ON ur.id = m.receiver " +
            "LEFT JOIN user us ON us.id = m.sender " +
            "WHERE (m.sender =:userId AND m.receiver =:companionId) OR " +
            "(m.sender =:companionId AND m.receiver =:userId) ORDER BY m.time";
    private static final String SQL_SELECT_FIRST_MESSAGE =
            "SELECT m.id as id, " +
            "m.time as time, " +
            "m.message as message, " +
            "ur.id as ur_id, " +
            "ur.username as ur_username, " +
            "ur.password as ur_password, " +
            "ur.first_name as ur_first_name, " +
            "ur.last_name as ur_last_name, " +
            "ur.date_of_birth as ur_date_of_birth, " +
            "ur.sex as ur_sex, " +
            "ur.phone as ur_phone, " +
            "ur.interests as ur_interests, " +
            "us.id as us_id, " +
            "us.username as us_username, " +
            "us.password as us_password, " +
            "us.first_name as us_first_name, " +
            "us.last_name as us_last_name, " +
            "us.date_of_birth as us_date_of_birth, " +
            "us.sex as us_sex, " +
            "us.phone as us_phone, " +
            "us.interests as us_interests " +
            "FROM messages m " +
            "LEFT JOIN user ur ON ur.id = m.receiver " +
            "LEFT JOIN user us ON us.id = m.sender " +
            "WHERE (m.sender =:senderId OR m.receiver =:receiverId)  " +
            "ORDER BY m.id DESC LIMIT 1 ";
    private static final String SQL_SAVE_MESSAGE =
            "INSERT INTO messages (time, message, sender, receiver) " +
            "VALUES (:time, :message, :sender, :receiver)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserDao userDao;

    public MessageDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                          UserDao userDao) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public List<Message> findAllRecentMessages(Long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        return namedParameterJdbcTemplate.query(SQL_SELECT_ALL_RECENT_MESSAGE_BY_ID, parameters, new MessageMapper());
    }

    @Override
    public List<Message> findConversation(Long userId, Long companionId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);
        parameters.addValue("companionId", companionId);
        return namedParameterJdbcTemplate.query(SQL_SELECT_CONVERSATION, parameters, new MessageMapper());
    }

    @Override
    public Message findFirstMessage(Long senderId, Long receiverId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("senderId", senderId);
        parameters.addValue("receiverId", receiverId);
        List<Message> messages = namedParameterJdbcTemplate.query(SQL_SELECT_FIRST_MESSAGE, parameters, new MessageMapper());
        if (messages != null && messages.size() > 0) {
            return messages.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public int saveMessage(Message message) {
        User sender = userDao.findUserByEmail(message.getSender().getEmail());
        User receiver = userDao.findUserByEmail(message.getReceiver().getEmail());
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("time", message.getTime());
        parameters.addValue("message", message.getMessage());
        parameters.addValue("sender", sender.getId());
        parameters.addValue("receiver", receiver.getId());
        return namedParameterJdbcTemplate.update(SQL_SAVE_MESSAGE, parameters);
    }
}
