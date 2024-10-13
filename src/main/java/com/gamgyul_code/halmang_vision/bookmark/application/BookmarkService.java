package com.gamgyul_code.halmang_vision.bookmark.application;

import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.ALREADY_BOOKMARKED;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.NOT_FOUND_SPOT_TRANSLATION;

import com.gamgyul_code.halmang_vision.bookmark.domain.BookmarkGenerator;
import com.gamgyul_code.halmang_vision.bookmark.domain.BookmarkRepository;
import com.gamgyul_code.halmang_vision.global.exception.HalmangVisionException;
import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.member.domain.MemberRepository;
import com.gamgyul_code.halmang_vision.member.dto.ApiMember;
import com.gamgyul_code.halmang_vision.spot.domain.Spot;
import com.gamgyul_code.halmang_vision.spot.domain.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final SpotRepository spotRepository;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkGenerator bookmarkGenerator;
    private final MemberRepository memberRepository;

    @Transactional
    public void createSpotBookmark(long spotId, ApiMember apiMember) {
        // 번역된 관광지 존재 여부 예외 처리
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_SPOT_TRANSLATION));

        // 멤버 예외 처리
        Member member = apiMember.toMember(memberRepository);

        boolean isAlreadyBookmarked = bookmarkRepository.existsBookmarkByMemberIdAndSpotId(member.getId(), spot.getId());

        if (isAlreadyBookmarked) {
            throw new HalmangVisionException(ALREADY_BOOKMARKED);
        }

        bookmarkRepository.save(bookmarkGenerator.generate(spot, member));
    }
}
