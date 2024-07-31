package com.gamgyul_code.halmang_vision.oauth2.dto;

import com.gamgyul_code.halmang_vision.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Oauth2Dto {

    private String username;
    private String email;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
