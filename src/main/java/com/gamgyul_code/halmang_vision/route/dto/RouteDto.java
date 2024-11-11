package com.gamgyul_code.halmang_vision.route.dto;

import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.route.domain.RecommendRoute;
import com.gamgyul_code.halmang_vision.route.domain.Route;
import com.gamgyul_code.halmang_vision.route.domain.RouteSpot;
import com.gamgyul_code.halmang_vision.spot.domain.Spot;
import com.gamgyul_code.halmang_vision.spot.domain.SpotTranslation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RouteDto {

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "경로 생성 요청")
    public static class CreateRouteRequest {

        @Schema(description = "경로 이름", example = "나의 경로")
        private String routeName;

        @Schema(description = "경로에 포함된 관광지 Id 리스트", example = "[1, 2, 3]")
        private List<Long> routeSpots;

        public Route toEntity(Member member) {
            return Route.builder()
                    .routeName(routeName)
                    .member(member)
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "추천 경로 생성 요청")
    public static class CreateRecommendRouteRequest {

        @Schema(description = "경로 이름", example = "나의 경로")
        private String routeName;

        @Schema(description = "경로에 포함된 관광지 Id 리스트", example = "[1, 2, 3]")
        private List<Long> routeSpots;

        @Schema(description = "경로 이미지 URL", example = "http://~~~.com/~~~.jpg")
        private String imgUrl;

        public RecommendRoute toEntity(Member member) {
            return RecommendRoute.builder()
                    .routeName(routeName)
                    .member(member)
                    .imgUrl(imgUrl)
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "경로-루트 중간 엔티티 생성 요청 (프론트 사용 X)")
    public static class CreateRouteSpotRequest {

        @Schema(description = "경로에 추가할 관광지 ID", example = "1")
        private Long spotId;

        public static RouteSpot toEntity(Spot spot, Route route) {
            return RouteSpot.builder()
                    .spot(spot)
                    .route(route)
                    .build();
        }

        public static RouteSpot toEntity(Spot spot, RecommendRoute recommendRoute) {
            return RouteSpot.builder()
                    .spot(spot)
                    .recommendRoute(recommendRoute)
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "경로 이름 수정 요청")
    public static class CreateRouteNameUpdateRequest {

        @Schema(description = "경로 이름", example = "나의 경로")
        private String routeName;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "경로 관광지 수정 요청")
    public static class CreateRouteSpotUpdateRequest {

        @Schema(description = "경로에 포함된 관광지 Id 리스트", example = "[1, 2, 3]")
        private List<Long> routeSpots;

    }


    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "내가 만든 경로 목록 조회")
    public static class MyRouteResponse {

        @Schema(description = "경로 ID", example = "1")
        private Long id;

        @Schema(description = "경로 이름", example = "나의 경로")
        private String routeName;

        @Schema(description = "루트 내 첫 번째 관광지의 이미지 URL", example = "http://~~~.com/~~~.jpg")
        private String imgUrl;

        public static MyRouteResponse fromEntity(Route route) {
            return MyRouteResponse.builder()
                    .id(route.getId())
                    .routeName(route.getRouteName())
                    .imgUrl(route.getRouteSpots().get(0).getSpot().getImgUrl())
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "내가 만든 경로 상세 조회")
    public static class MyRouteDetailResponse {

        @Schema(description = "경로 ID", example = "1")
        private Long id;

        @Schema(description = "경로 이름", example = "나의 경로")
        private String routeName;

        @Schema(description = "루트 내 관광지 리스트", example = "[{spotId: 1, spotName: '성산일출봉', imgUrl: 'http://~~~.com/~~~.jpg'}, ...]")
        private List<RouteSpotResponse> routeSpots;

        public static MyRouteDetailResponse fromEntity(Route route, List<RouteSpotResponse> routeSpots) {
            return MyRouteDetailResponse.builder()
                    .id(route.getId())
                    .routeName(route.getRouteName())
                    .routeSpots(routeSpots)
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "경로 내 관광지 정보")
    public static class RouteSpotResponse {

        @Schema(description = "관광지 ID", example = "1")
        private Long spotId;

        @Schema(description = "관광지 번역 ID", example = "1")
        private Long spotTranslationId;

        @Schema(description = "관광지 이름", example = "성산일출봉")
        private String spotName;

        @Schema(description = "관광지 간단 설명", example = "설문대할망이 태어난 장소")
        private String simpleExplanation;

        public static RouteSpotResponse fromEntity(SpotTranslation spotTranslation) {
            return RouteSpotResponse.builder()
                    .spotId(spotTranslation.getSpot().getId())
                    .spotTranslationId(spotTranslation.getId())
                    .spotName(spotTranslation.getName())
                    .simpleExplanation(spotTranslation.getSimpleExplanation())
                    .build();
        }
    }
}
