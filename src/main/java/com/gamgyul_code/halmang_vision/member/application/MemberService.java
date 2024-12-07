package com.gamgyul_code.halmang_vision.member.application;

import com.gamgyul_code.halmang_vision.global.jwt.JwtTokenProvider;
import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.member.domain.MemberRepository;
import com.gamgyul_code.halmang_vision.member.dto.ApiMember;
import com.gamgyul_code.halmang_vision.spot.domain.LanguageCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public RedirectView logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        clearCookies(request, response);
        return new RedirectView("/");
    }

    @Transactional
    public void updateLanguageCode(String languageCode, ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);

        languageCode = languageCode.toUpperCase();
        LanguageCode enumLanguageCode = LanguageCode.valueOf(languageCode);

        member.setLanguageCode(enumLanguageCode);
    }

    private void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        for (Cookie cookie : request.getCookies()) {
            cookie.setValue("");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    public String reissueAccessToken(HttpServletRequest request, ApiMember apiMember) {
        long memberId = apiMember.toMember(memberRepository).getId();

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (name.equals("refreshToken")) {
                String refreshToken = cookie.getValue();
                jwtTokenProvider.validateToken(refreshToken);
                return jwtTokenProvider.reissueAccessToken(refreshToken, memberId);
            }
        }
        return null;
    }
}