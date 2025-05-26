package com.searchforest.user.service;


import com.searchforest.user.domain.User;
import com.searchforest.user.domain.UserLogin;
import com.searchforest.user.repository.PaperHistoryRepository;
import com.searchforest.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PaperHistoryRepository paperHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(UserLogin userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("이미 사용 중인 사용자 이름입니다.");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

        User user = User.builder()
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .username(userRequest.getUsername())
                .build();

        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }


}
