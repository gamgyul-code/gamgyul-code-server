package com.gamgyul_code.halmang_vision.route.domain;

import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.spot.domain.LanguageCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRouteRepository extends JpaRepository<RecommendRoute, Long> {
    List<RecommendRoute> findAllByLanguageCode(LanguageCode languageCode);

    boolean existsByRouteName(String routeName);

    List<RecommendRoute> findAllByMember(Member member);
}
