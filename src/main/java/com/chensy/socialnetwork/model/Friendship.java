package com.chensy.socialnetwork.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {

    private Long id;

    private User userSender;

    private User userReceiver;

    private Boolean accepted;

}
