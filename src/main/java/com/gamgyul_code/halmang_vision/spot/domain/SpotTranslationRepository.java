package com.gamgyul_code.halmang_vision.spot.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotTranslationRepository extends JpaRepository<SpotTranslation, Long> {
    SpotTranslation findByName(String name);

    boolean existsBySpotIdAndLanguageCode(Long spotId, LanguageCode languageCode);
}
