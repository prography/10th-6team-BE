package com.prography.zone_2_be.domain.user.entity;

import com.prography.zone_2_be.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements UserDetails {
    @Column(nullable = false, updatable = false)
    @Getter
    private String oauth2Key;

    @Column(nullable = false)
    private String email;

    @Column
    @Setter
    private long birth;

    @Column
    @Setter
    private int height;

    @Column
    @Setter
    private int weight;

    @Column
    @Setter
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.getValue()));
    }

    @Override
    public String getUsername() {
        // 사용자를 식별할 수 있는 고유한 값을 반환 (보통 ID나 이메일, 여기선 oauth2Key)
        return this.oauth2Key;
    }

    @Override
    public String getPassword() {
        // OAuth2 기반이므로 비밀번호는 사용하지 않음
        return null;
    }

    // --- 계정 상태 관련 메서드 (일단 true로 반환) ---
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    @Builder
    public User (String oauth2Key, String email, Role role){
        this.oauth2Key = oauth2Key;
        this.email = email;
        this.role = role; // Default role is User
    }

    public static User forRegister(String oauth2Key, String email) {
        return User.builder()
                .oauth2Key(oauth2Key)
                .email(email)
                .role(Role.User) // Default role is User
                .build();
    }
}
