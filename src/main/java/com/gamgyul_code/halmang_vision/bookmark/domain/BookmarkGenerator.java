package com.gamgyul_code.halmang_vision.bookmark.domain;

import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.spot.domain.Spot;
import com.gamgyul_code.halmang_vision.spot.domain.SpotCategory;
import org.springframework.stereotype.Component;

@Component
public class BookmarkGenerator {

    public Bookmark generate(Spot spot, Member member, SpotCategory spotCategory) {
        return Bookmark.builder()
                .member(member)
                .spot(spot)
                .spotCategory(spotCategory)
                .build();
    }
}
