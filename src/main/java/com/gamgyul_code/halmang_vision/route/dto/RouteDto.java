package com.gamgyul_code.halmang_vision.route.dto;

import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.route.domain.Route;
import com.gamgyul_code.halmang_vision.route.domain.RouteSpot;
import com.gamgyul_code.halmang_vision.spot.domain.Spot;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
    }
}
