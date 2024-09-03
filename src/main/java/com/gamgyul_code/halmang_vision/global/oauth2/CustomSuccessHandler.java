package com.gamgyul_code.halmang_vision.global.oauth2;


import com.gamgyul_code.halmang_vision.global.jwt.JwtTokenProvider;
import com.gamgyul_code.halmang_vision.global.oauth2.dto.CustomOAuth2User;
import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.member.domain.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.gamgyul_code.halmang_vision.global.jwt.JwtTokenProvider.createAccessCookie;
import static com.gamgyul_code.halmang_vision.global.jwt.JwtTokenProvider.createCookie;


@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${oauth2.success.redirect-url}")
    private String redirectUrl;

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // Oauth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Member member = memberRepository.findByUsername(username);
        String accessToken = jwtTokenProvider.createAccessToken(authentication, member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);


        response.addCookie(createCookie(refreshToken));
        response.addCookie(createAccessCookie(accessToken));
        response.sendRedirect(redirectUrl);
    }
}
