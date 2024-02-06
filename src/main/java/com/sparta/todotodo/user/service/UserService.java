package com.sparta.todotodo.user.service;

import com.sparta.todotodo.user.dto.SignupRequestDto;
import com.sparta.todotodo.user.entity.User;
import com.sparta.todotodo.user.entity.UserRole;
import com.sparta.todotodo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            return new ResponseEntity<>("중복된 사용자명 존재", HttpStatus.BAD_REQUEST);
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            return new ResponseEntity<>("중복된 이메일명 존재", HttpStatus.BAD_REQUEST);
        }

        // 사용자 ROLE 확인
        UserRole role = UserRole.USER;
        if (requestDto.isAdmin()) {
            // ADMIN_TOKEN, 관리자임을 증명할 때 사용
            String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                return new ResponseEntity<>("관리자 암호가 틀립니다.", HttpStatus.BAD_REQUEST);
            }
            role = UserRole.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);

        ResponseEntity<String> res = new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
        System.out.println("res = " + res);
        return res;
    }

}