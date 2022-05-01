package com.chensy.socialnetwork.dao;

import com.chensy.socialnetwork.model.Message;

import java.util.List;

public interface MessageDao {
    List<Message> findAllRecentMessages(Long id);

    List<Message> findConversation(Long userId, Long companionId);

    Message findFirstBySenderIdOrReceiverIdOrderByIdDesc(Long senderId, Long receiverId);

    void saveMessage(Message message);
}
