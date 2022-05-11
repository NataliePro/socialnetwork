package com.chensy.socialnetwork.dao.impl;

import com.chensy.socialnetwork.model.Gender;
import com.chensy.socialnetwork.model.Message;
import com.chensy.socialnetwork.model.Role;
import com.chensy.socialnetwork.model.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MessageDaoImplTest {

    @Autowired
    private MessageDaoImpl messageDao;

    @Test
    void findAllRecentMessagesTest() {
        List<Message> allRecentMessages = messageDao.findAllRecentMessages(1L);
        assertNotNull(allRecentMessages);
    }

    @Test
    void findConversationTest() {
        List<Message> conversation = messageDao.findConversation(1L, 2L);
        assertNotNull(conversation);
    }

    @Test
    void findFirstMessageTest() {
        Message message = messageDao.findFirstMessage(1L, 2L);
        assertNotNull(message);
    }

    @Test
    void saveMessageTest() {
        int message = messageDao.saveMessage(new Message()
                .setMessage("bla")
                .setReceiver(createUser())
                .setSender(createUser())
                .setTime(LocalDateTime.now()));
        assertTrue(message > 0);
    }

    private User createUser() {
        return new User()
                .setEmail("ben@mail.ru")
                .setFirstName("111")
                .setLastName("111")
                .setSex(Gender.FEMALE)
                .setPassword("111")
                .setDob(LocalDate.now());
    }
}