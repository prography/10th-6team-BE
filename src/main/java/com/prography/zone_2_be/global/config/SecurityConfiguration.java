package com.prography.zone_2_be.global.config;

import com.prography.zone_2_be.global.exception.AccessDeniedHandlerImpl;
import com.prography.zone_2_be.global.exception.SecurityAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {
    private final JwtAuthFilter jwtAuthFilter;
    private final SecurityAuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandlerImpl accessDeniedHandler;

    /**
     * 공개 API를 위한 SecurityFilterChain (JWT 필터 비활성화)
     * @Order(1)을 통해 다른 체인보다 우선 순위를 높게 설정합니다.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
        http
                // 이 체인은 /api/v1/health, /api/v1/auth/** 경로의 요청만 처리하도록 지정합니다.
                .securityMatcher("/api/v1/health", "/api/v1/auth/**")
                .authorizeHttpRequests(req -> req
                        // 해당 경로의 모든 요청을 허용합니다.
                        .anyRequest().permitAll()
                )
                // 이 체인에서는 세션을 사용하지 않으므로 csrf, cors 비활성화가 안전합니다.
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);

        log.info("Public SecurityFilterChain Bean CREATED!");
        return http.build();
    }


    /**
     * JWT 인증이 필요한 API를 위한 SecurityFilterChain
     * @Order(2)로 두 번째 우선순위를 가집니다.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 이 체인은 /api/v1/로 시작하는 모든 요청을 처리합니다.
                // 하지만 위의 publicFilterChain이 우선순위가 높으므로, 해당 경로들은 먼저 처리되어 이 체인에 도달하지 않습니다.
                .securityMatcher("/api/v1/**")
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        // 이 체인으로 들어온 모든 요청은 인증을 요구합니다.
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                // 예외 처리 핸들러를 등록합니다.
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint) // 인증 실패 핸들러
                        .accessDeniedHandler(accessDeniedHandler)         // 인가 실패 핸들러
                );

        log.info("Secured SecurityFilterChain Bean CREATED!");
        return http.build();
    }

    /**
     * JwtAuthFilter를 Spring Security의 필터 체인에서만 사용하고,
     * 서블릿 컨테이너에 자동으로 등록되지 않도록 설정합니다.
     * @param filter 등록을 비활성화할 필터
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilterRegistration(JwtAuthFilter filter) {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false); // 서블릿 컨테이너의 필터 체인에 등록하지 않음
        return registration;
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
}
