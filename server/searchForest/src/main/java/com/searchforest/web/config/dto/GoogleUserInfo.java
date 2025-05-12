package com.searchforest.web.config.dto;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override public String getId() { return (String) attributes.get("sub"); }
    @Override public String getEmail() { return (String) attributes.get("email"); }
    @Override public String getNickname() { return (String) attributes.get("name"); }
}

