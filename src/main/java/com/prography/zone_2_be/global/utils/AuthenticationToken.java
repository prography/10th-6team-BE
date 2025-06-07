package com.prography.zone_2_be.global.utils;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private final Object credential;

    public AuthenticationToken(Object principal, Object credential, Collection<? extends GrantedAuthority> authorities) {
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
    public Object getPrincipal() {
        return this.principal;
    }
}
