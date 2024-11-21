package com.gamgyul_code.halmang_vision.bookmark.presentation;

import com.gamgyul_code.halmang_vision.bookmark.application.BookmarkService;
import com.gamgyul_code.halmang_vision.global.utils.AuthPrincipal;
import com.gamgyul_code.halmang_vision.member.dto.ApiMember;
import com.gamgyul_code.halmang_vision.route.application.RouteService;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.RouteResponse;
import com.gamgyul_code.halmang_vision.spot.domain.SpotCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
@Tag(name = "bookmark", description = "북마크 API")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final RouteService routeService;

    @Operation(summary = "관광지 북마크 생성", description = "해당 회원에게 관광지 북마크를 생성한다.")
    @PostMapping("/spots/{spotId}/{spotCategory}")
    public void createSpotBookmark(@PathVariable Long spotId, @PathVariable SpotCategory spotCategory,
                                   @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        bookmarkService.createSpotBookmark(spotId, spotCategory, apiMember);
    }

    @Operation(summary = "관광지 북마크 삭제", description = "해당 회원에게 관광지 북마크를 삭제한다.")
    @DeleteMapping("/spots/{spotId}")
    public void deleteSpotBookmark(@PathVariable Long spotId, @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        bookmarkService.deleteSpotBookmark(spotId, apiMember);
    }

    @Operation(summary = "추천 경로 북마크 생성", description = "해당 회원에게 추천 경로 북마크를 생성한다.")
    @PostMapping("/recommend-routes/{recommendRouteId}")
    public void createRecommendRouteBookmark(@PathVariable Long recommendRouteId,
                                            @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        bookmarkService.createRecommendRouteBookmark(recommendRouteId, apiMember);
    }

    @Operation(summary = "추천 경로 북마크 삭제", description = "해당 회원에게 추천 경로 북마크를 삭제한다.")
    @DeleteMapping("/recommend-routes/{recommendRouteId}")
    public void deleteRecommendRouteBookmark(@PathVariable Long recommendRouteId,
                                            @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        bookmarkService.deleteRecommendRouteBookmark(recommendRouteId, apiMember);
    }

    @GetMapping("/recommend-routes")
    @Operation(summary = "추천 경로 북마크 조회", description = "사용자의 추천 경로 북마크를 조회한다.")
    public List<RouteResponse> findAllRecommendRouteBookmarks(@Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        return routeService.findAllMyRecommendRoutes(apiMember);
    }
}
