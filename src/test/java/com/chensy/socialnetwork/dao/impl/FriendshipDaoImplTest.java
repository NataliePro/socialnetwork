package com.chensy.socialnetwork.dao.impl;

import com.chensy.socialnetwork.dao.FriendshipDao;
import com.chensy.socialnetwork.model.Friendship;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class FriendshipDaoImplTest {

    @Autowired
    private FriendshipDao friendshipDao;

    @Test
    void findAcceptedFriendshipUsers() {
        List<Friendship> acceptedFriendshipUsers = friendshipDao.findAcceptedFriendshipUsers(1L);
        assertNotNull(acceptedFriendshipUsers);
    }

    @Test
    void deleteFriendRequests() {
        int i = friendshipDao.deleteFriendRequests(1L, 3L);
        assertTrue(i>0);
    }

    @Test
    void addFriendship() {
        int i = friendshipDao.addFriendship(1L, 4L);
        assertTrue(i>0);
    }

    @Test
    void addToFriends() {
        int i = friendshipDao.addToFriends(1L, 5L);
        assertTrue(i>0);
    }

    @Test
    void checkFriendshipExists() {
        assertTrue(friendshipDao.checkFriendshipExists(1L, 2L));
    }

    @Test
    void findAllByUserSenderIdOrUserReceiverId() {
        List<Friendship> acceptedFriendshipUsers = friendshipDao.findAllByUserSenderIdOrUserReceiverId(1L, 2L);
        assertNotNull(acceptedFriendshipUsers);
    }
}