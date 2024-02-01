package com.sparta.todotodo.user.controller;

import com.sparta.todotodo.user.dto.LoginRequestDto;
import com.sparta.todotodo.user.dto.SignupRequestDto;
import com.sparta.todotodo.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/signup")
    public String signup(SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);

        return "redirect:/user/login";
    }

    @PostMapping("/login")
    public String login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            userService.login(loginRequestDto, response);
        } catch (Exception e) {
            return "redirect:/user/login?error";
        }

        return "redirect:/";
    }
}