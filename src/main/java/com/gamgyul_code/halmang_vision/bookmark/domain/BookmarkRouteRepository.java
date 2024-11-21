package com.gamgyul_code.halmang_vision.bookmark.domain;

import com.gamgyul_code.halmang_vision.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRouteRepository extends JpaRepository<BookmarkRoute, Long> {
    boolean existsBookmarkByMemberIdAndRecommendRouteId(Long memberId, Long recommendRouteId);

    void deleteByMemberIdAndRecommendRouteId(Long memberId, Long recommendRouteId);

    List<BookmarkRoute> findAllByMember(Member member);
}
