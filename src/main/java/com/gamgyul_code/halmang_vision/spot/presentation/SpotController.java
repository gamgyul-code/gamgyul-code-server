package com.gamgyul_code.halmang_vision.spot.presentation;

import com.gamgyul_code.halmang_vision.global.utils.AuthPrincipal;
import com.gamgyul_code.halmang_vision.member.dto.ApiMember;
import com.gamgyul_code.halmang_vision.spot.application.SpotService;
import com.gamgyul_code.halmang_vision.spot.domain.SpotCategory;
import com.gamgyul_code.halmang_vision.spot.domain.SpotRegion;
import com.gamgyul_code.halmang_vision.spot.dto.SpotDto.CreateSpotTranslationRequest;
import com.gamgyul_code.halmang_vision.spot.dto.SpotDto.CreateSpotRequest;
import com.gamgyul_code.halmang_vision.spot.dto.SpotDto.SpotTranslationDetailResponse;
import com.gamgyul_code.halmang_vision.spot.dto.SpotDto.SimpleSpotTranslationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spots")
@RequiredArgsConstructor
@Tag(name = "spot", description = "관광지 API")
public class SpotController {

    private final SpotService spotService;

    @PostMapping
    @Operation(summary = "관광지 생성", description = "관광지를 생성한다(개발용).")
    public void createSpot(@Valid @RequestBody CreateSpotRequest createSpotRequest) {
        spotService.createSpot(createSpotRequest);
    }

    @PostMapping("/translations/{spotId}")
    @Operation(summary = "관광지 번역 생성", description = "관광지 번역을 생성한다(개발용).")
    public void createSpotTranslation(@PathVariable Long spotId, @Valid @RequestBody CreateSpotTranslationRequest createSpotTranslationRequest) {
        spotService.createSpotTranslation(createSpotTranslationRequest, spotId);
    }

    @GetMapping("/{spotId}")
    @Operation(summary = "관광지 상세 조회", description = "번역된 관광지 상세 정보를 조회한다.")
    public SpotTranslationDetailResponse findById(@PathVariable Long spotId, @Parameter(hidden = true) @AuthPrincipal
                                            ApiMember apiMember) {
        return spotService.findById(spotId, apiMember);
    }

    @GetMapping("/tale/{category}")
    @Operation(summary = "설화별 관광지 목록 조회", description = "설화별 관광지를 조회한다.(/halmang, /love, /history, /myth)")
    public List<SimpleSpotTranslationResponse> findByCategory(@PathVariable SpotCategory category, @Parameter(hidden = true)
                                                            @AuthPrincipal ApiMember apiMember) {
        return spotService.findAllSpotsByCategory(category, apiMember);
    }

    @GetMapping("/bookmarks")
    @Operation(summary = "즐겨찾기한 관광지 목록 조회", description = "즐겨찾기한 관광지를 조회한다.")
    public List<SimpleSpotTranslationResponse> findByBookmarks(@Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        return spotService.findAllSpotsByBookmark(apiMember);
    }

    @GetMapping("/regions/{spotRegion}")
    @Operation(summary = "지역별 관광지 목록 조회", description = "지역별 관광지를 조회한다. (western-jeju-city, jeju-city, eastern-jeju-city,"
            + "western-seogwipo-city, seogwipo-city, eastern-seogwipo-city)")
    public List<SimpleSpotTranslationResponse> findByRegion(@PathVariable String spotRegion, @Parameter(hidden = true)
                                                        @AuthPrincipal ApiMember apiMember) {
        return spotService.findAllSpotsByRegion(spotRegion, apiMember);
    }
}
