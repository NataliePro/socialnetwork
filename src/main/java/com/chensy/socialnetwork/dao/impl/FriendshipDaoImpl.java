package com.chensy.socialnetwork.dao.impl;

import com.chensy.socialnetwork.dao.FriendshipDao;
import com.chensy.socialnetwork.mapper.FriendshipMapper;
import com.chensy.socialnetwork.model.Friendship;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FriendshipDaoImpl implements FriendshipDao {

    private static final String SQL_SELECT_FRIENDSHIP_BY_USER_ID =
            "SELECT f.id as id, " +
            "f.accepted as accepted, " +
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
            "FROM friendship f " +
            "LEFT JOIN user ur ON ur.id = f.user_receiver " +
            "LEFT JOIN user us ON us.id = f.user_sender " +
            "WHERE f.accepted = 1 AND (f.user_receiver =:user_id OR f.user_sender =:user_id) ";
    private static final String SQL_DELETE_FRIENDSHIP_BY_USER_ID =
            "DELETE FROM friendship f " +
            "WHERE (f.user_sender = :user_id AND f.user_receiver = :friend_id) OR (f.user_sender = :friend_id AND f.user_receiver = :user_id)";
    private static final String SQL_ADD_FRIENDSHIP_BY_USER_ID =
            "INSERT INTO friendship (user_sender, user_receiver, accepted) VALUES (:user_id, :friend_id, true)";
    private static final String SQL_ADD_TO_FRIENDS =
            "INSERT INTO friendship (user_sender, user_receiver, accepted) VALUES (:user_id, :friend_id, false)";
    private static final String SQL_CHECK_FRIENDSHIP_EXIST =
            "SELECT case when count(*)> 0 then true else false end " +
            "FROM friendship f " +
            "WHERE (f.user_sender = :user_id AND f.user_receiver = :friend_id) OR (f.user_sender = :friend_id AND f.user_receiver = :user_id)";
    private static final String SQL_SELECT_FRIENDSHIP_BY_SENDER_RECEIVER =
            "SELECT f.id as id, " +
                    "f.accepted as accepted, " +
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
                    "FROM friendship f " +
                    "LEFT JOIN user ur ON ur.id = f.user_receiver " +
                    "LEFT JOIN user us ON us.id = f.user_sender " +
                    "WHERE f.user_receiver =:user_receiver_id OR f.user_sender =:user_sender_id ";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public FriendshipDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Friendship> findAcceptedFriendshipUsers(Long userId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", userId);
        return namedParameterJdbcTemplate.query(SQL_SELECT_FRIENDSHIP_BY_USER_ID, parameters, new FriendshipMapper());
    }

    @Override
    public int deleteFriendRequests(Long userId, Long friendId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", userId);
        parameters.addValue("friend_id", friendId);
        return namedParameterJdbcTemplate.update(SQL_DELETE_FRIENDSHIP_BY_USER_ID, parameters);
    }

    @Override
    public int addFriendship(Long userId, Long friendId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", userId);
        parameters.addValue("friend_id", friendId);
        return namedParameterJdbcTemplate.update(SQL_ADD_FRIENDSHIP_BY_USER_ID, parameters);
    }

    @Override
    public int addToFriends(Long userId, Long friendId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", userId);
        parameters.addValue("friend_id", friendId);
        return namedParameterJdbcTemplate.update(SQL_ADD_TO_FRIENDS, parameters);
    }

    @Override
    public boolean checkFriendshipExists(Long userId, Long friendId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", userId);
        parameters.addValue("friend_id", friendId);
        return namedParameterJdbcTemplate.queryForObject(SQL_CHECK_FRIENDSHIP_EXIST, parameters, Boolean.class);
    }

    @Override
    public List<Friendship> findAllByUserSenderIdOrUserReceiverId(Long userSenderId, Long userReceiverId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_sender_id", userSenderId);
        parameters.addValue("user_receiver_id", userReceiverId);
        return namedParameterJdbcTemplate.query(SQL_SELECT_FRIENDSHIP_BY_SENDER_RECEIVER, parameters, new FriendshipMapper());
    }
}
