package com.gamgyul_code.halmang_vision.spot.application;

import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.ALREADY_EXIST_SPOT;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.ALREADY_EXIST_SPOT_TRANSLATION;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.ALREADY_EXIST_SPOT_TRANSLATION_NAME;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.NOT_FOUND_SPOT;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.NOT_FOUND_SPOT_TRANSLATION;

import com.gamgyul_code.halmang_vision.bookmark.domain.Bookmark;
import com.gamgyul_code.halmang_vision.bookmark.domain.BookmarkRepository;
import com.gamgyul_code.halmang_vision.global.exception.HalmangVisionException;
import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.member.domain.MemberRepository;
import com.gamgyul_code.halmang_vision.member.dto.ApiMember;
import com.gamgyul_code.halmang_vision.spot.domain.LanguageCode;
import com.gamgyul_code.halmang_vision.spot.domain.Spot;
import com.gamgyul_code.halmang_vision.spot.domain.SpotCategory;
import com.gamgyul_code.halmang_vision.spot.domain.SpotRegion;
import com.gamgyul_code.halmang_vision.spot.domain.SpotRepository;
import com.gamgyul_code.halmang_vision.spot.domain.SpotTranslation;
import com.gamgyul_code.halmang_vision.spot.domain.SpotTranslationRegion;
import com.gamgyul_code.halmang_vision.spot.domain.SpotTranslationRepository;
import com.gamgyul_code.halmang_vision.spot.dto.SpotDto.CreateSpotRequest;
import com.gamgyul_code.halmang_vision.spot.dto.SpotDto.CreateSpotTranslationRequest;
import com.gamgyul_code.halmang_vision.spot.dto.SpotDto.SpotTranslationDetailResponse;
import com.gamgyul_code.halmang_vision.spot.dto.SpotDto.SimpleSpotTranslationResponse;
import java.util.List;
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

        if (spotRepository.findByName(spot.getName()).isPresent()) {
            throw new HalmangVisionException(ALREADY_EXIST_SPOT);
        }

        spotRepository.save(spot);
    }

    @Transactional
    public void createSpotTranslation(CreateSpotTranslationRequest createSpotTranslationRequest, Long spotId) {

        // 해당 관광지 정보 존재 여부 확인
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_SPOT));

        // 이미 해당 언어로 번역된 정보가 존재하는지 확인
        LanguageCode languageCode = createSpotTranslationRequest.getLanguageCode();

        if (spotTranslationRepository.existsBySpotIdAndLanguageCode(spotId, languageCode)) {
            throw new HalmangVisionException(ALREADY_EXIST_SPOT_TRANSLATION);
        }

        // 번역된 관광지 이름이 중복인지 확인
        if (spotTranslationRepository.findByName(createSpotTranslationRequest.getName()) != null) {
            throw new HalmangVisionException(ALREADY_EXIST_SPOT_TRANSLATION_NAME);
        }

        SpotTranslationRegion spotTranslationRegion = SpotTranslationRegion.from(languageCode, spot.getSpotRegion());

        SpotTranslation spotTranslation = createSpotTranslationRequest.toEntity(spot, spotTranslationRegion);

        spotTranslationRepository.save(spotTranslation);

    }

    @Transactional(readOnly = true)
    public SpotTranslationDetailResponse findById(Long spotId, ApiMember apiMember) {
        SpotTranslation spotTranslation = spotTranslationRepository.findById(spotId)
                .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_SPOT_TRANSLATION));

        long memberId = apiMember.toMember(memberRepository).getId();
        boolean isBookmarked = bookmarkRepository.existsBookmarkByMemberIdAndSpotId(memberId, spotId);

        return SpotTranslationDetailResponse.fromEntity(spotTranslation, isBookmarked);
    }

    @Transactional(readOnly = true)
    public List<SimpleSpotTranslationResponse> findAllSpotsByCategory(SpotCategory spotCategory, ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);
        LanguageCode languageCode = member.getLanguageCode();

        List<SpotTranslation> spotTranslations =
                spotTranslationRepository.findAllBySpot_SpotCategoryAndLanguageCode(spotCategory, languageCode);

        return spotTranslations.stream()
                .map(spotTranslation -> {
                    long memberId = member.getId();
                    boolean isBookmarked = bookmarkRepository.existsBookmarkByMemberIdAndSpotId(memberId, spotTranslation.getId());
                    return SimpleSpotTranslationResponse.fromEntity(spotTranslation, isBookmarked);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SimpleSpotTranslationResponse> findAllSpotsByBookmark(ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);
        Long memberId = member.getId();
        LanguageCode languageCode = member.getLanguageCode();

        List<Bookmark> bookmarks = bookmarkRepository.findAllByMemberId(memberId);

        return bookmarks.stream()
                .map(bookmark -> {
                    Spot spot = bookmark.getSpot();
                    SpotTranslation spotTranslation = spotTranslationRepository.findBySpotAndLanguageCode(spot, languageCode)
                            .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_SPOT_TRANSLATION));

                    return SimpleSpotTranslationResponse.fromEntity(spotTranslation, true);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SimpleSpotTranslationResponse> findAllSpotsByRegion(SpotRegion spotRegion, ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);
        LanguageCode languageCode = member.getLanguageCode();

        List<SpotTranslation> spotTranslations =
                spotTranslationRepository.findAllBySpot_SpotRegionAndLanguageCode(spotRegion, languageCode);

        return spotTranslations.stream()
                .map(spotTranslation -> {
                    long memberId = member.getId();
                    boolean isBookmarked = bookmarkRepository.existsBookmarkByMemberIdAndSpotId(memberId, spotTranslation.getId());
                    return SimpleSpotTranslationResponse.fromEntity(spotTranslation, isBookmarked);
                })
                .toList();
    }
}
