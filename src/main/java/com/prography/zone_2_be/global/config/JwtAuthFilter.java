package com.prography.zone_2_be.global.config;

import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.domain.user.service.UserService;
import com.prography.zone_2_be.global.exception.SecurityAuthenticationEntryPoint;
import com.prography.zone_2_be.global.utils.AuthenticationToken;
import com.prography.zone_2_be.global.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter { // OncePerRequestFilter -> 한 번 실행 보장
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final SecurityAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // Bearer 토큰이 없는 경우, 다음 필터로 바로 진행
            filterChain.doFilter(request, response);
            return; // 현재 필터의 작업은 여기서 종료
        }

        // Bearer 토큰이 있는 경우, 토큰 검증 및 SecurityContext 설정 로직
        String token = authorizationHeader.substring(7);
        if (jwtUtil.validateToken(token)) {
            try {
                String oAuth2Key = jwtUtil.getOAuth2Key(token);

                User user = userService.findUserByOAuth2Key(oAuth2Key);
                AuthenticationToken authenticationToken =
                        new AuthenticationToken(user.getOauth2Key(), null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("JwtAuthFilter: Successfully authenticated user '{}'", user.getUsername()); //getUsername()이 있다고 가정

            } catch (Exception e) {
                log.error("JwtAuthFilter: Error processing JWT.", e);
                SecurityContextHolder.clearContext();
            }
        } else {
            log.warn("JwtAuthFilter: Invalid JWT token.");
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response); // 모든 로직 후 다음 필터로 진행
    }
}
