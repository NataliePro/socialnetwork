package com.chensy.socialnetwork.dto;


import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDTO {
    private static final long serialVersionUID = 1L;

    private String oldPassword;
    private String password;
    private String passwordConfirmation;
}
