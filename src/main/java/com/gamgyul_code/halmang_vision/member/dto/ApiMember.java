package com.gamgyul_code.halmang_vision.member.dto;

import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.NOT_FOUND_MEMBER;

import com.gamgyul_code.halmang_vision.global.exception.HalmangVisionException;
import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.member.domain.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class ApiMember {

    private String username;
    private Long memberId;

    public Member toMember(MemberRepository memberRepository) {
        return memberRepository.findByIdAndUsername(memberId, username)
                .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_MEMBER));
    }
}
