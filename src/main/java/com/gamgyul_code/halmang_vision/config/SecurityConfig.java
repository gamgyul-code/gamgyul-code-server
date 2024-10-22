package com.gamgyul_code.halmang_vision.config;

import com.gamgyul_code.halmang_vision.global.jwt.JwtAccessDeniedHandler;
import com.gamgyul_code.halmang_vision.global.jwt.JwtAuthenticationEntryPoint;
import com.gamgyul_code.halmang_vision.global.jwt.JwtAuthenticationFilter;
import com.gamgyul_code.halmang_vision.global.jwt.JwtTokenProvider;
import com.gamgyul_code.halmang_vision.global.oauth2.CustomSuccessHandler;
import com.gamgyul_code.halmang_vision.global.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private static final long MAX_AGE_SEC = 3600;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                    exception.accessDeniedHandler(jwtAccessDeniedHandler);
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("http://localhost:8080");
        configuration.addAllowedOriginPattern("http://localhost:3000"); // 프론트 쪽에서 허용
        configuration.addAllowedOriginPattern("http://43.200.126.36"); // 도메인 주소
        configuration.addAllowedOriginPattern("http://43.200.126.36:8080");
        configuration.addAllowedOriginPattern("http://43.200.126.36:3000");
        configuration.addAllowedHeader("Accept");
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("refreshToken");
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("Origin");
        configuration.addAllowedHeader("Cookie");
        configuration.addAllowedHeader("X-Requested-With");
        configuration.addAllowedHeader("Access-Control-Allow-Origin");
        configuration.addAllowedHeader("Access-Control-Allow-Credentials");
        configuration.addAllowedHeader("Access-Control-Allow-Methods");
        configuration.addAllowedHeader("Access-Control-Allow-Headers");
        configuration.addAllowedHeader("Host");
        configuration.addAllowedHeader("Connection");
        configuration.addAllowedHeader("Accept-Encoding");
        configuration.addAllowedHeader("Accept-Language");
        configuration.addAllowedHeader("Referer");
        configuration.addAllowedHeader("User-Agent");
        configuration.addAllowedHeader("Sec-Fetch-Mode");
        configuration.addAllowedHeader("Sec-Fetch-Site");;
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("refreshToken");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(MAX_AGE_SEC);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}