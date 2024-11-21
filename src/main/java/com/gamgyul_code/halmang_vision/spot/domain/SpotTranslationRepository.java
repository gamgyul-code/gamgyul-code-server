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

    @Query("SELECT st FROM SpotTranslation st " +
            "JOIN st.spot s " +
            "JOIN RouteSpot rs ON rs.spot = s " +
            "WHERE rs.route.id = :routeId AND st.languageCode = :languageCode")
    List<SpotTranslation> findByRouteIdAndLanguageCode(@Param("routeId") Long routeId,
                                                       @Param("languageCode") LanguageCode languageCode);

    @Query("SELECT st FROM SpotTranslation st " +
            "JOIN st.spot s " +
            "JOIN RouteSpot rs ON rs.spot = s " +
            "WHERE rs.recommendRoute.id= :recommendRouteId AND st.languageCode = :languageCode")
    List<SpotTranslation> findByRecommendRouteIdAndLanguageCode(@Param("recommendRouteId") Long recommendRouteId,
                                                                @Param("languageCode") LanguageCode languageCode);

    List<SpotTranslation> findAllBySpot_SpotCategoryAndLanguageCode(SpotCategory spotCategory, LanguageCode languageCode);
}
