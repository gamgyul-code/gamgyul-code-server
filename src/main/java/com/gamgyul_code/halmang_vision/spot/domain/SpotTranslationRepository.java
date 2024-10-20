package com.gamgyul_code.halmang_vision.spot.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpotTranslationRepository extends JpaRepository<SpotTranslation, Long> {
    SpotTranslation findByName(String name);

    Optional<SpotTranslation> findBySpotAndLanguageCode(Spot spot, LanguageCode languageCode);

    boolean existsBySpotIdAndLanguageCode(Long spotId, LanguageCode languageCode);

    @Query("SELECT st FROM SpotTranslation st " +
            "JOIN st.spot s " +
            "WHERE :spotCategory IN elements(s.spotCategory) " +
            "AND st.languageCode = :languageCode")
    List<SpotTranslation> findAllBySpotCategoryAndLanguageCode(
            @Param("spotCategory") SpotCategory spotCategory,
            @Param("languageCode") LanguageCode languageCode);

    List<SpotTranslation> findAllBySpot_SpotRegionAndLanguageCode(SpotRegion spotRegion, LanguageCode languageCode);
}
