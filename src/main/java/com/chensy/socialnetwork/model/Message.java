package com.chensy.socialnetwork.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private int id;

    private LocalDateTime time;

    private String message;

    private User sender;

    private User receiver;

}
