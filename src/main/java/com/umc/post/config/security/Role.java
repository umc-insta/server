package com.umc.post.config.security;

import lombok.Getter;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;

    public String getValue() {
        return value;
    }

    Role(String value) {
        this.value = value;
    }
}