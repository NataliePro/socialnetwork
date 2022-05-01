package com.chensy.socialnetwork.dao;

import com.chensy.socialnetwork.model.Friendship;
import com.chensy.socialnetwork.model.User;

import java.util.List;

public interface FriendshipDao {
    List<Friendship> findAcceptedFriendshipUsers(Long userId);

    void deleteFriendRequests(User user, Long friendId);

    void addFriendship(User user, Long friendId);

    void addToFriends(User user, Long friendId);

    boolean checkFriendshipExists(User user, User friend);

    List<Friendship> findAllByUserSenderIdOrUserReceiverId(Long userSenderId, Long userReceiverId);
}
