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

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        if (userRepository.findByUsername(username).isPresent()) {
            return new ResponseEntity<>("중복된 사용자명 존재", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByEmail(email).isPresent()) {
            return new ResponseEntity<>("중복된 이메일명 존재", HttpStatus.BAD_REQUEST);
        }

        // 사용자 ROLE 확인
        UserRole role = UserRole.USER;

        if (requestDto.isAdmin()) {
            String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                return new ResponseEntity<>("관리자 암호가 틀립니다.", HttpStatus.BAD_REQUEST);
            }

            role = UserRole.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);

        return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
    }
}