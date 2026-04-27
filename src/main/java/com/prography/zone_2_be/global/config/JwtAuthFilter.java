package com.prography.zone_2_be.global.config;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.prography.zone_2_be.domain.auth.service.AuthService;
import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.global.utils.AuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	private final AuthService authService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authorizationHeader.substring(7);
		log.info("JwtAuthFilter: 인증 토큰 '{}' 추출", token);
		User user = authService.getAuthenticatedUser(token);

		// 인증 성공 시에만 아래 코드가 실행됩니다.
		AuthenticationToken authenticationToken =
			new AuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		log.info("JwtAuthFilter: 사용자 '{}' 인증 성공", user.getUsername());

		// 인증 성공 후, 다음 필터로 요청을 전달합니다.
		filterChain.doFilter(request, response);
	}
}