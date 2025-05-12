package com.searchforest.web.config;

import com.searchforest.user.domain.User;
import com.searchforest.user.repository.UserRepository;
import com.searchforest.web.config.dto.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 기본 유저 정보 로드
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // 어떤 소셜 서비스인지 (google, kakao 등)
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 사용자 고유 식별자 속성명 (google은 sub, kakao는 id)
        String userNameAttribute = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oauth2User.getAttributes();

        // 공통화된 사용자 정보 파싱
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, attributes);
        if (userInfo.getEmail() == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_email"), "이메일을 찾을 수 없습니다.");
        }

        // 기존 회원 여부 확인
        Optional<User> optionalUser = userRepository.findByEmail(userInfo.getEmail());
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            // 소셜 로그인 provider 정보 업데이트 (선택)
        } else {
            user = User.builder()
                    .email(userInfo.getEmail())
                    .username(userInfo.getNickname())
                    .password("NO_PASSWORD") // 소셜 로그인은 비밀번호 사용 안 함
                    .provider(provider)
                    .providerId(userInfo.getId())
                    .build();
            userRepository.save(user);
        }

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                userNameAttribute
        );
    }
}
