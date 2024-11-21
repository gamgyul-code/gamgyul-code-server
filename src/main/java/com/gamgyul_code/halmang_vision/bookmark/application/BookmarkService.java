package com.gamgyul_code.halmang_vision.bookmark.application;

import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.ALREADY_BOOKMARKED;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.NOT_FOUND_BOOKMARK;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.NOT_FOUND_SPOT;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.NOT_FOUND_SPOT_TRANSLATION;

import com.gamgyul_code.halmang_vision.bookmark.domain.BookmarkGenerator;
import com.gamgyul_code.halmang_vision.bookmark.domain.BookmarkRepository;
import com.gamgyul_code.halmang_vision.global.exception.HalmangVisionException;
import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.member.domain.MemberRepository;
import com.gamgyul_code.halmang_vision.member.dto.ApiMember;
import com.gamgyul_code.halmang_vision.spot.domain.Spot;
import com.gamgyul_code.halmang_vision.spot.domain.SpotCategory;
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
    public void createSpotBookmark(long spotId, SpotCategory spotCategory, ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);
        Spot spot = findSpotById(spotId);

        validateBookmarkExists(member, spot, false);

        bookmarkRepository.save(bookmarkGenerator.generate(spot, member, spotCategory));
    }

    @Transactional
    public void deleteSpotBookmark(long spotId, ApiMember apiMember) {

        Member member = apiMember.toMember(memberRepository);
        Spot spot = findSpotById(spotId);

        validateBookmarkExists(member, spot, true);

        bookmarkRepository.deleteByMemberIdAndSpotId(member.getId(), spot.getId());
    }

    private Spot findSpotById(Long spotId) {
        return spotRepository.findById(spotId)
                .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_SPOT));
    }

    private void validateBookmarkExists(Member member, Spot spot, boolean shouldExist) {
        boolean isAlreadyBookmarked = bookmarkRepository
                .existsBookmarkByMemberIdAndSpotId(member.getId(), spot.getId());

        if (shouldExist && !isAlreadyBookmarked) {
            throw new HalmangVisionException(NOT_FOUND_BOOKMARK);
        }
        if (!shouldExist && isAlreadyBookmarked) {
            throw new HalmangVisionException(ALREADY_BOOKMARKED);
        }
    }
}
