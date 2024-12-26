package com.gamgyul_code.halmang_vision.map.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
@Tag(name = "map", description = "지도 API")
public class MapController {
    /*

    private final MapService mapService;

    @GetMapping("/geocoding")
    public Mono<CoordinatesResponse> geocodingTest() {
        return mapService.getCoordinates("서울특별시 강남구 역삼동 736-1");
    }


     */
}
