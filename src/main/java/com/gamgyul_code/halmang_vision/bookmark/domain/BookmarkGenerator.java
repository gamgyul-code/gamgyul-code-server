package com.gamgyul_code.halmang_vision.bookmark.domain;

import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.spot.domain.Spot;
import org.springframework.stereotype.Component;

@Component
public class BookmarkGenerator {

    public Bookmark generate(Spot spot, Member member) {
        return Bookmark.builder()
                .member(member)
                .spot(spot)
                .build();
    }
}
