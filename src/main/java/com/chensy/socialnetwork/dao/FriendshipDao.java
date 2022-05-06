package com.chensy.socialnetwork.dao;

import com.chensy.socialnetwork.model.Friendship;
import com.chensy.socialnetwork.model.User;

import java.util.List;

public interface FriendshipDao {
    List<Friendship> findAcceptedFriendshipUsers(Long userId);

    void deleteFriendRequests(Long userId, Long friendId);

    void addFriendship(Long userId, Long friendId);

    void addToFriends(Long userId, Long friendId);

    boolean checkFriendshipExists(Long userId, Long friend_id);

    List<Friendship> findAllByUserSenderIdOrUserReceiverId(Long userSenderId, Long userReceiverId);
}
