package com.gamgyul_code.halmang_vision.member.dto;

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
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다.")); // TODO : global exception
    }
}
