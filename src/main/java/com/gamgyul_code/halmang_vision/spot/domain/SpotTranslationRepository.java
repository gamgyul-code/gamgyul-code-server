package com.gamgyul_code.halmang_vision.spot.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotTranslationRepository extends JpaRepository<SpotTranslation, Long> {
    SpotTranslation findByName(String name);

    Optional<SpotTranslation> findBySpotAndLanguageCode(Spot spot, LanguageCode languageCode);

    boolean existsBySpotIdAndLanguageCode(Long spotId, LanguageCode languageCode);

    List<SpotTranslation> findAllBySpot_SpotCategoryAndLanguageCode(SpotCategory spotCategory, LanguageCode languageCode);
}
