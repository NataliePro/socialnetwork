package com.chensy.socialnetwork.dao.impl;

import com.chensy.socialnetwork.dao.FriendshipDao;
import com.chensy.socialnetwork.model.Friendship;
import com.chensy.socialnetwork.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FriendshipDaoImpl implements FriendshipDao {

    @Override
    public List<Friendship> findAcceptedFriendshipUsers(Long userId) {
        return null;
    }

    @Override
    public void deleteFriendRequests(User user, Long friendId) {

    }

    @Override
    public void addFriendship(User user, Long friendId) {

    }

    @Override
    public void addToFriends(User user, Long friendId) {

    }

    @Override
    public boolean checkFriendshipExists(User user, User friend) {
        return false;
    }

    @Override
    public List<Friendship> findAllByUserSenderIdOrUserReceiverId(Long userSenderId, Long userReceiverId) {
        return null;
    }
}
