package com.gamgyul_code.halmang_vision.map.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MapDto {
    /*

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "X, Y 좌표 응답")
    public static class CoordinatesResponse {

        @Schema(description = "위도 좌표", example = "126.123456")
        private double latitude;

        @Schema(description = "경도 좌표", example = "37.123456")
        private double longitude;


        public static CoordinatesResponse fromNaverGeocodingApiResponse(double latitude, double longitude) {
            return CoordinatesResponse.builder()
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
        }
    }

     */
}
