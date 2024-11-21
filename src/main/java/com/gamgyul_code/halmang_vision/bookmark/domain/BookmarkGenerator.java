package com.gamgyul_code.halmang_vision.bookmark.domain;

import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.route.domain.RecommendRoute;
import com.gamgyul_code.halmang_vision.spot.domain.Spot;
import com.gamgyul_code.halmang_vision.spot.domain.SpotCategory;
import org.springframework.stereotype.Component;

@Component
public class BookmarkGenerator {

    public BookmarkSpot generate(Spot spot, Member member, SpotCategory spotCategory) {
        return BookmarkSpot.builder()
                .member(member)
                .spot(spot)
                .spotCategory(spotCategory)
                .build();
    }

    public BookmarkRoute generate(RecommendRoute recommendRoute, Member member) {
        return BookmarkRoute.builder()
                .member(member)
                .recommendRoute(recommendRoute)
                .build();
    }
}
