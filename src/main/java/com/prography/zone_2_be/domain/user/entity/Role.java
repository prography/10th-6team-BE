package com.prography.zone_2_be.domain.user.entity;

public enum Role {
    ADMIN("admin"),
    User("user");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
