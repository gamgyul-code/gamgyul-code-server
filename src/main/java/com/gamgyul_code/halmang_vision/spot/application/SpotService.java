package com.gamgyul_code.halmang_vision.spot.application;

import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.NOT_FOUND_SPOT;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.NOT_FOUND_SPOT_TRANSLATION;

import com.gamgyul_code.halmang_vision.bookmark.domain.BookmarkRepository;
import com.gamgyul_code.halmang_vision.global.exception.HalmangVisionException;
import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.member.domain.MemberRepository;
import com.gamgyul_code.halmang_vision.member.dto.ApiMember;
import com.gamgyul_code.halmang_vision.spot.domain.Spot;
import com.gamgyul_code.halmang_vision.spot.domain.SpotRepository;
import com.gamgyul_code.halmang_vision.spot.domain.SpotTranslation;
import com.gamgyul_code.halmang_vision.spot.domain.SpotTranslationRepository;
import com.gamgyul_code.halmang_vision.spot.dto.SpotDto.CreateSpotRequest;
import com.gamgyul_code.halmang_vision.spot.dto.SpotDto.CreateSpotTranslationRequest;
import com.gamgyul_code.halmang_vision.spot.dto.SpotDto.SpotTranslationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;
    private final SpotTranslationRepository spotTranslationRepository;
    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createSpot(CreateSpotRequest createSpotRequest) {
        Spot spot = createSpotRequest.toEntity();

        spotRepository.save(spot);
    }

    @Transactional
    public void createSpotTranslation(CreateSpotTranslationRequest createSpotTranslationRequest, Long spotId) {
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_SPOT));
/*
        if (spotTranslationRepository.findById(spotId).isPresent() &&
                spotTranslationRepository.findById(spotId).get().getLanguageCode().equals(createSpotTranslationRequest.getLanguageCode())) {
            throw new IllegalArgumentException("이미 해당 언어로 번역된 정보가 존재합니다.");
        }

        if (spotTranslationRepository.findByName(createSpotTranslationRequest.getName()) != null) {
            spotTranslationRepository.deleteById(spotId); // TODO: 다시 체크
        }


 */

        SpotTranslation spotTranslation = createSpotTranslationRequest.toEntity(spot);

        spotTranslationRepository.save(spotTranslation);

    }

    @Transactional(readOnly = true)
    public SpotTranslationResponse findById(Long spotId, ApiMember apiMember) {
        SpotTranslation spotTranslation = spotTranslationRepository.findById(spotId)
                .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_SPOT_TRANSLATION));

        long memberId = apiMember.toMember(memberRepository).getId();
        boolean isBookmarked = bookmarkRepository.existsBookmarkByMemberIdAndSpotId(memberId, spotId);

        return SpotTranslationResponse.fromEntity(spotTranslation, isBookmarked);
    }
}
