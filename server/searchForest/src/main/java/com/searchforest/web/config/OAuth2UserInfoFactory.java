package com.searchforest.web.config;

import com.searchforest.web.config.dto.GoogleUserInfo;
import com.searchforest.web.config.dto.KakaoUserInfo;
import com.searchforest.web.config.dto.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String provider, Map<String, Object> attributes) {
        return switch (provider) {
            case "google" -> new GoogleUserInfo(attributes);
            case "kakao" -> new KakaoUserInfo(attributes);
            default -> throw new IllegalArgumentException("지원하지 않는 OAuth2 provider: " + provider);
        };
    }
}
