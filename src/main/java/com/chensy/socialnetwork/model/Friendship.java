package com.chensy.socialnetwork.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {

    private Long id;

    private User userSender;

    private User userReceiver;

    private Boolean accepted;

}
