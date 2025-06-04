package com.prography.zone_2_be.domain.user.entity;

import com.prography.zone_2_be.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Entity
public class User extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String oAuth2Key;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private long birth;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Getter
    public List<SimpleGrantedAuthority> authorities;

    public void setAuthoritiesWithRole() {
        this.authorities.add(new SimpleGrantedAuthority(role.getValue()));
    }
}
