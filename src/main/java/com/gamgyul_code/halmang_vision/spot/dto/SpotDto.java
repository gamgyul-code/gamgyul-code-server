package com.gamgyul_code.halmang_vision.spot.dto;

import com.gamgyul_code.halmang_vision.spot.domain.LanguageCode;
import com.gamgyul_code.halmang_vision.spot.domain.Spot;
import com.gamgyul_code.halmang_vision.spot.domain.SpotCategory;
import com.gamgyul_code.halmang_vision.spot.domain.SpotRegion;
import com.gamgyul_code.halmang_vision.spot.domain.SpotTranslation;
import com.gamgyul_code.halmang_vision.spot.domain.SpotTranslationRegion;
import com.gamgyul_code.halmang_vision.spot.domain.TravelerStatistics;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class SpotDto {

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "관광지 기본 정보 생성 요청")
    public static class CreateSpotRequest {

        //TODO: 관광지 위도, 경도를 저장해야 하는가? -> 프론트에게 질문

        @Schema(description = "관광지 이름", example = "성산일출봉")
        private String name;

        @Schema(description = "관광지 카테고리", example = "halmang")
        private List<SpotCategory> spotCategory;

        @Schema(description = "관광지 지역", example = "JEJU_CITY")
        private SpotRegion spotRegion;

        @Schema(description = "관광지 이미지 URL", example = "http://~~~.com/~~~.jpg")
        private String imgUrl;

        @Schema(description = "관광객 통계", example = "HIGH")
        private TravelerStatistics travelerStatistics;

        @Schema(description = "운영 시간", example = "09:00 - 18:00")
        private String openingHours;

        @Schema(description = "관광지 전화번호", example = "064-783-0959")
        private String phoneNumber;

        @Schema(description = "위도", example = "33.458")
        private double latitude;

        @Schema(description = "경도", example = "126.942")
        private double longitude;

        public Spot toEntity() {
            return Spot.builder()
                    .name(name)
                    .spotCategory(spotCategory)
                    .spotRegion(spotRegion)
                    .imgUrl(imgUrl)
                    .travelerStatistics(travelerStatistics)
                    .openingHours(openingHours)
                    .phoneNumber(phoneNumber)
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "관광지 번역 정보 생성 요청")
    public static class CreateSpotTranslationRequest {

        @Schema(description = "관광지 이름", example = "성산일출봉")
        private String name;

        @Schema(description = "언어 코드", example = "KOR")
        private LanguageCode languageCode;

        @Schema(description = "요약", example = "역사 관련 장소")
        private String summary;

        @Schema(description = "주소", example = "서귀포시 성산읍 일출로 284-12")
        private String address;

        @Schema(description = "입장료", example = "무료")
        private String fee;

        @Schema(description = "신화 읽을거리", example = "옛날 옛적에...")
        private String taleStory;

        @Schema(description = "역사 읽을거리", example = "1943년에는 일본군이 이곳을 요새화 하기 위해 일출봉 해안절벽에 24개의 굴을 팠습니다...")
        private String historyStory;

        @Schema(description = "지형 읽을거리", example = "성산일출봉은 화산활동으로 만들어진 화산섬의 일부로, 성산항에서 동쪽으로 2km 떨어진 곳에 위치하고 있습니다...")
        private String topographyStory;

        @Schema(description = "주의사항", example = "화산섬이므로 주변 지역은 화산재로 이루어져 있습니다. 주변 지역은 화산재로 이루어져 있으므로 발을 다치지 않도록 주의하세요.")
        private String caution;

        @Schema(description = "관광지 간단 설명", example = "설문대할망이 태어난 장소")
        private String simpleExplanation;

        public SpotTranslation toEntity(Spot spot, SpotTranslationRegion spotTranslationRegion) {
            return SpotTranslation.builder()
                    .name(name)
                    .languageCode(languageCode)
                    .spotTranslationRegion(spotTranslationRegion)
                    .summary(summary)
                    .address(address)
                    .fee(fee)
                    .taleStory(taleStory)
                    .historyStory(historyStory)
                    .topographyStory(topographyStory)
                    .caution(caution)
                    .simpleExplanation(simpleExplanation)
                    .spot(spot)
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "관광지 상세 정보(번역) 응답")
    public static class SpotTranslationDetailResponse {

        @Schema(description = "관광지 번역 id", example = "1")
        private Long spotTranslationId;

        @Schema(description = "관광지 id(번역 이전)", example = "1")
        private Long spotId;

        @Schema(description = "관광지 카테고리", example = "HISTORY")
        private List<SpotCategory> spotCategory;

        @Schema(description = "관광지 이미지 URL", example = "http://~~~.com/~~~.jpg")
        private String imgUrl;

        @Schema(description = "관광객 통계", example = "HIGH")
        private TravelerStatistics travelerStatistics;

        @Schema(description = "운영 시간", example = "09:00 - 18:00")
        private String openingHours;

        @Schema(description = "관광지 전화번호", example = "064-783-0959")
        private String phoneNumber;

        @Schema(description = "관광지 이름", example = "성산일출봉")
        private String name;

        @Schema(description = "언어 코드", example = "KOR")
        private LanguageCode languageCode;

        @Schema(description = "요약", example = "역사 관련 장소")
        private String summary;

        @Schema(description = "주소", example = "서귀포시 성산읍 일출로 284-12")
        private String address;

        @Schema(description = "입장료", example = "무료")
        private String fee;

        @Schema(description = "신화 읽을거리", example = "옛날 옛적에...")
        private String taleStory;

        @Schema(description = "역사 읽을거리", example = "1943년에는 일본군이 이곳을 요새화 하기 위해 일출봉 해안절벽에 24개의 굴을 팠습니다...")
        private String historyStory;

        @Schema(description = "지형 읽을거리", example = "성산일출봉은 화산활동으로 만들어진 화산섬의 일부로, 성산항에서 동쪽으로 2km 떨어진 곳에 위치하고 있습니다...")
        private String topographyStory;

        @Schema(description = "주의사항", example = "화산섬이므로 주변 지역은 화산재로 이루어져 있습니다. 주변 지역은 화산재로 이루어져 있으므로 발을 다치지 않도록 주의하세요.")
        private String caution;

        @Schema(description = "북마크 여부", example = "true")
        private boolean bookmarked;

        @Schema(description = "관광지 카테고리", example = "HISTORY, LOVE")
        private List<SpotCategory> spotCategories;

        public static SpotTranslationDetailResponse fromEntity(SpotTranslation spotTranslation, boolean isBookmarked) {
            return SpotTranslationDetailResponse.builder()
                    .spotTranslationId(spotTranslation.getId())
                    .spotId(spotTranslation.getSpot().getId())
                    .spotCategory(spotTranslation.getSpot().getSpotCategory())
                    .imgUrl(spotTranslation.getSpot().getImgUrl())
                    .travelerStatistics(spotTranslation.getSpot().getTravelerStatistics())
                    .openingHours(spotTranslation.getSpot().getOpeningHours())
                    .phoneNumber(spotTranslation.getSpot().getPhoneNumber())
                    .name(spotTranslation.getName())
                    .languageCode(spotTranslation.getLanguageCode())
                    .summary(spotTranslation.getSummary())
                    .address(spotTranslation.getAddress())
                    .fee(spotTranslation.getFee())
                    .taleStory(spotTranslation.getTaleStory())
                    .historyStory(spotTranslation.getHistoryStory())
                    .topographyStory(spotTranslation.getTopographyStory())
                    .caution(spotTranslation.getCaution())
                    .bookmarked(isBookmarked)
                    .spotCategories(spotTranslation.getSpot().getSpotCategory())
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "간단한 관광지 정보(번역) 응답")
    public static class SimpleSpotTranslationResponse {

            @Schema(description = "관광지 번역 id", example = "1")
            private Long spotTranslationId;

            @Schema(description = "관광지 id(번역 이전)", example = "1")
            private Long spotId;

            @Schema(description = "관광지 이름", example = "성산일출봉")
            private String name;

            @Schema(description = "관광지 이미지 URL", example = "http://~~~.com/~~~.jpg")
            private String imgUrl;

            @Schema(description = "관광지 간단 설명", example = "설문대할망이 태어난 장소")
            private String simpleExplanation;

            @Schema(description = "북마크 여부", example = "true")
            private boolean bookmarked;

            @Schema(description = "관광지 카테고리", example = "HISTORY, LOVE")
            private List<SpotCategory> spotCategories;

            public static SimpleSpotTranslationResponse fromEntity(SpotTranslation spotTranslation, boolean isBookmarked) {
                return SimpleSpotTranslationResponse.builder()
                        .spotTranslationId(spotTranslation.getId())
                        .spotId(spotTranslation.getSpot().getId())
                        .name(spotTranslation.getName())
                        .imgUrl(spotTranslation.getSpot().getImgUrl())
                        .simpleExplanation(spotTranslation.getSimpleExplanation())
                        .bookmarked(isBookmarked)
                        .spotCategories(spotTranslation.getSpot().getSpotCategory())
                        .build();
            }
    }


    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "메인 필터링 관광지 정보(번역) 응답")
    public static class MainScreenFilteredSpotTranslationResponse {

        @Schema(description = "관광지 번역 id", example = "1")
        private Long spotTranslationId;

        @Schema(description = "관광지 id(번역 이전)", example = "1")
        private Long spotId;

        @Schema(description = "관광지 이름", example = "성산일출봉")
        private String name;

        @Schema(description = "관광지 이미지 URL", example = "http://~~~.com/~~~.jpg")
        private String imgUrl;

        @Schema(description = "연간 관광객 정보", example = "HIGH")
        private TravelerStatistics travelerStatistics;

        @Schema(description = "북마크 여부", example = "true")
        private boolean bookmarked;

        @Schema(description = "관광지 카테고리", example = "HISTORY, LOVE")
        private List<SpotCategory> spotCategories;

        @Schema(description = "위도", example = "33.458")
        private double latitude;

        @Schema(description = "경도", example = "126.942")
        private double longitude;

        public static MainScreenFilteredSpotTranslationResponse fromEntity(SpotTranslation spotTranslation, boolean isBookmarked) {
            return MainScreenFilteredSpotTranslationResponse.builder()
                    .spotTranslationId(spotTranslation.getId())
                    .spotId(spotTranslation.getSpot().getId())
                    .name(spotTranslation.getName())
                    .imgUrl(spotTranslation.getSpot().getImgUrl())
                    .travelerStatistics(spotTranslation.getSpot().getTravelerStatistics())
                    .bookmarked(isBookmarked)
                    .spotCategories(spotTranslation.getSpot().getSpotCategory())
                    .latitude(spotTranslation.getSpot().getLatitude())
                    .longitude(spotTranslation.getSpot().getLongitude())
                    .build();
        }
    }
}
