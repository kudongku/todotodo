package com.sparta.todotodo.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "유저네임 에러")
    private String username;

    @Pattern(regexp = "[a-zA-Z1-9]{8,15}", message = "비밀번호 에러")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "이메일 에러")
    private String email;

    private boolean admin = false;

    private String adminToken = "";
}
