package com.user.userAuth.dtos;

import lombok.Data;


@Data
public class SignupRequestDto {
    private String name;
    private String email;
    private String password;

}
