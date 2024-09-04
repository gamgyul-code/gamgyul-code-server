package com.gamgyul_code.halmang_vision.spot.application;

import com.gamgyul_code.halmang_vision.spot.domain.Spot;
import com.gamgyul_code.halmang_vision.spot.domain.SpotRepository;
import com.gamgyul_code.halmang_vision.spot.domain.SpotTranslation;
import com.gamgyul_code.halmang_vision.spot.domain.SpotTranslationRepository;
import com.gamgyul_code.halmang_vision.spot.dto.SpotDto.CreateSpotRequest;
import com.gamgyul_code.halmang_vision.spot.dto.SpotDto.CreateSpotTranslationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;
    private final SpotTranslationRepository spotTranslationRepository;

    @Transactional
    public void createSpot(CreateSpotRequest createSpotRequest) {
        Spot spot = createSpotRequest.toEntity();

        spotRepository.save(spot);
    }

    @Transactional
    public void createSpotTranslation(CreateSpotTranslationRequest createSpotTranslationRequest, Long spotId) {
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new IllegalArgumentException("해당 관광지가 존재하지 않습니다.")); // TODO: 예외 처리
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
}
