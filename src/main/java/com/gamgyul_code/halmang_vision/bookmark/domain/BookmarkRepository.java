package com.gamgyul_code.halmang_vision.bookmark.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsBookmarkByMemberIdAndSpotId(Long memberId, Long spotId);
}
