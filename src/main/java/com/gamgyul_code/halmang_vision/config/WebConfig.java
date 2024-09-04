package com.gamgyul_code.halmang_vision.config;

import com.gamgyul_code.halmang_vision.global.jwt.ApiMemberArgumentResolver;
import com.gamgyul_code.halmang_vision.global.jwt.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ApiMemberArgumentResolver(jwtTokenProvider));
    }
}