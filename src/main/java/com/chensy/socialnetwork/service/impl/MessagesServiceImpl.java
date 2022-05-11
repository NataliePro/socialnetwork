package com.chensy.socialnetwork.service.impl;


import com.chensy.socialnetwork.converters.MessageDtoToMessageConverter;
import com.chensy.socialnetwork.converters.MessageToMessageDtoConverter;
import com.chensy.socialnetwork.dao.MessageDao;
import com.chensy.socialnetwork.dto.MessageDTO;
import com.chensy.socialnetwork.model.Message;
import com.chensy.socialnetwork.model.User;
import com.chensy.socialnetwork.service.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MessagesServiceImpl implements MessagesService {

    private final MessageDao messageDao;
    private final MessageToMessageDtoConverter messageToMessageDtoConverter;
    private final MessageDtoToMessageConverter messageDtoToMessageConverter;

    @Override
    public Collection<MessageDTO> findAllRecentMessages(Long id) {
        Iterable<Message> all = messageDao.findAllRecentMessages(id);
        Map<User, MessageDTO> map = new HashMap<>();
        all.forEach(m -> {
            MessageDTO messageDTO = messageToMessageDtoConverter.convert(m, id);
            User user = m.getSender().getId().equals(id) ? m.getReceiver() : m.getSender();
            map.put(user, messageDTO);
        });
        return map.values();
    }

    @Override
    public List<MessageDTO> findConversation(Long userId, Long companionId) {
        List<Message> all = messageDao.findConversation(userId, companionId);
        List<MessageDTO> messages = new LinkedList<>();
        all.forEach(m -> messages.add(messageToMessageDtoConverter.convert(m, userId)));
        return messages;
    }

    @Override
    public MessageDTO getRecentMessage(Long id) {
        Message message = messageDao.findFirstMessage(id, id);
        MessageDTO messageDTO = messageToMessageDtoConverter.convert(message, id);
        return messageDTO;
    }

    @Override
    public void postMessage(MessageDTO messageDTO) {
        Message message = messageDtoToMessageConverter.convert(messageDTO);
        messageDao.saveMessage(message);
    }
}
