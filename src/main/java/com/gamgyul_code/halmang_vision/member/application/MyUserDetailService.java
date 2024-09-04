package com.gamgyul_code.halmang_vision.member.application;

import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.NOT_FOUND_MEMBER;

import com.gamgyul_code.halmang_vision.global.exception.HalmangVisionException;
import com.gamgyul_code.halmang_vision.member.domain.CustomUserDetails;
import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));
        return new CustomUserDetails(member);
    }

    public CustomUserDetails loadUserByIdAndUsername(Long memberId, String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByIdAndUsername(memberId, username)
                .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_MEMBER));
        return new CustomUserDetails(member);
    }
}
