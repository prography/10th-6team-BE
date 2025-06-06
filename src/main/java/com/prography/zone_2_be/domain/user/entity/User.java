package com.prography.zone_2_be.domain.user.entity;

import com.prography.zone_2_be.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Entity
@Builder
public class User extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String oAuth2Key;

    @Column(nullable = false)
    private String email;

    @Column
    private long birth;

    @Column
    private int height;

    @Column
    private int weight;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Getter
    @Transient // 컬럼으로 사용하지 않음을 선언
    public List<SimpleGrantedAuthority> authorities;

    public void setAuthoritiesWithRole() {
        this.authorities.add(new SimpleGrantedAuthority(role.getValue()));
    }

    public static User forRegister(String oAuth2Key, String email) {
        return User.builder()
                .oAuth2Key(oAuth2Key)
                .email(email)
                .role(Role.User) // Default role is User
                .build();
    }
}
