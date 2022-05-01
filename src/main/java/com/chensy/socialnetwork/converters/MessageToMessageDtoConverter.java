package com.chensy.socialnetwork.converters;

import com.chensy.socialnetwork.dto.MessageDTO;
import com.chensy.socialnetwork.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageToMessageDtoConverter {

    private final UserToUserDtoConverter userToUserDtoConverter;

    public MessageDTO convert(Message message, Long id) {
        if(message == null) {
            return null;
        }
        return new MessageDTO()
                         .setTime(message.getTime())
                         .setMessage(message.getMessage())
                         .setReceiver(userToUserDtoConverter.convert(message.getReceiver()))
                         .setSender(userToUserDtoConverter.convert(message.getSender()))
                         .setCompanionId(id.equals(message.getSender().getId()) ?
                                      message.getReceiver().getId() : message.getSender().getId());
    }
}
