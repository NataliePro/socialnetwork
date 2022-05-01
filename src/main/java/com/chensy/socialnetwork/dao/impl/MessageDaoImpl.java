package com.chensy.socialnetwork.dao.impl;

import com.chensy.socialnetwork.dao.MessageDao;
import com.chensy.socialnetwork.model.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageDaoImpl implements MessageDao {
    @Override
    public List<Message> findAllRecentMessages(Long id) {
        return null;
    }

    @Override
    public List<Message> findConversation(Long userId, Long companionId) {
        return null;
    }

    @Override
    public Message findFirstBySenderIdOrReceiverIdOrderByIdDesc(Long senderId, Long receiverId) {
        return null;
    }

    @Override
    public void saveMessage(Message message) {

    }
}
