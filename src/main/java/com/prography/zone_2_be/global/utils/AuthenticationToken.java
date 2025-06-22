package com.prography.zone_2_be.global.utils;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AuthenticationToken extends AbstractAuthenticationToken {
    private final UserDetails principal;
    private final Object credential;

    public AuthenticationToken(UserDetails principal, Object credential, Collection<? extends GrantedAuthority> authorities) {
        super(authorities); // 이후 custom한 authority 클래스 사용
        this.principal = principal;
        this.credential = credential;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credential;
    }

    @Override
    public UserDetails getPrincipal() {
        return this.principal;
    }
}
