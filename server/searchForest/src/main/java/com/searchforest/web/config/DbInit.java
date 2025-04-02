package com.searchforest.web.config;

import com.searchforest.user.domain.User;
import com.searchforest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbInit implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        userRepository.findByEmail("admin@test.com").orElseGet(() ->
            userRepository.save(User.builder()
                            .email("admin@test.com")
                            .password(passwordEncoder.encode("1q2w3e4r"))
                            .build())
        );


    }
}
