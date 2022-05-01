package com.chensy.socialnetwork.converters;

import com.chensy.socialnetwork.dto.MessageDTO;
import com.chensy.socialnetwork.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageDtoToMessageConverter implements Converter<MessageDTO, Message> {

    private final UserDtoToUserConverter userDtoToUserConverter;

    @Override
    public Message convert(MessageDTO messageDTO) {
        if(messageDTO == null) {
            return null;
        }

        return new Message()
                      .setTime(messageDTO.getTime())
                      .setMessage(messageDTO.getMessage())
                      .setSender(userDtoToUserConverter.convert(messageDTO.getSender()))
                      .setReceiver(userDtoToUserConverter.convert(messageDTO.getReceiver()));
    }
}
