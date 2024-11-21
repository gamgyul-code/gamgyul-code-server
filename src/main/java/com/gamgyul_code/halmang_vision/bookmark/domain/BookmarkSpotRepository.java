package com.gamgyul_code.halmang_vision.bookmark.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkSpotRepository extends JpaRepository<BookmarkSpot, Long> {
    boolean existsBookmarkByMemberIdAndSpotId(Long memberId, Long spotId);

    List<BookmarkSpot> findAllByMemberId(Long memberId);

    void deleteByMemberIdAndSpotId(Long memberId, Long spotId);
}
