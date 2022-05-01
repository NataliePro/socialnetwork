package com.chensy.socialnetwork.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private static final long serialVersionUID = 1L;

    private LocalDateTime time;
    private String message;
    private UserDTO sender;
    private UserDTO receiver;
    private Long companionId;
}
