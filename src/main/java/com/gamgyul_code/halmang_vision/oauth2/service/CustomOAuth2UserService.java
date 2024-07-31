package com.gamgyul_code.halmang_vision.oauth2.service;


import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.member.domain.MemberRepository;
import com.gamgyul_code.halmang_vision.oauth2.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static com.gamgyul_code.halmang_vision.config.SecurityConfig.passwordEncoder;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final String OAUTH2_PASSWORD = "OAUTH2_PASSWORD";
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("Loading user: {}", oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Oauth2Response oauth2Response = switch (registrationId) {
            case "naver" -> new NaverResponse(oAuth2User.getAttributes());
            case "google" -> new GoogleResponse(oAuth2User.getAttributes());
            case "kakao" -> new KakaoResponse(oAuth2User.getAttributes());
            default -> null;
        };

        String username = oauth2Response.getProvider() + " " + oauth2Response.getProviderId();
        Member findMember = memberRepository.findByUsername(username);

        if (findMember == null) {
            String encodedPassword = passwordEncoder().encode(OAUTH2_PASSWORD);
            Oauth2Dto oauth2Dto = Oauth2Dto.builder()
                    .email(oauth2Response.getEmail())
                    .username(username)
                    .password(encodedPassword)
                    .build();

            Member newMember = oauth2Dto.toEntity();
            memberRepository.save(newMember);

            return new CustomOAuth2User(oauth2Dto);
        }

        else { // 회원이 존재할 떄
            Oauth2Dto oauth2Dto = Oauth2Dto.builder()
                    .email(findMember.getEmail())
                    .username(findMember.getUsername())
                    .build();
            return new CustomOAuth2User(oauth2Dto);
        }
    }
}
