package com.gamgyul_code.halmang_vision.route.presentation;

import com.gamgyul_code.halmang_vision.global.utils.AuthPrincipal;
import com.gamgyul_code.halmang_vision.member.dto.ApiMember;
import com.gamgyul_code.halmang_vision.route.application.RouteService;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.CreateRouteNameUpdateRequest;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.CreateRouteRequest;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.CreateRouteSpotUpdateRequest;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.MyRouteDetailResponse;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.MyRouteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
@Tag(name = "route", description = "경로 API")
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    @Operation(summary = "내 경로 생성", description = "사용자에게 경로를 생성한다.")
    public void createRoute(@RequestBody CreateRouteRequest createRouteRequest, @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        routeService.createRoute(createRouteRequest, apiMember);
    }

    @GetMapping
    @Operation(summary = "내 경로 조회", description = "사용자가 만든 경로를 조회한다.")
    public List<MyRouteResponse> findAllMyRoutes(@Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        return routeService.findAllMyRoutes(apiMember);
    }

    @GetMapping("/{routeId}")
    @Operation(summary = "내 경로 상세 조회", description = "경로 상세 정보를 조회한다.")
    public MyRouteDetailResponse findMyRouteDetail(@PathVariable Long routeId, @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        return routeService.findRouteDetail(routeId, apiMember);
    }

    @PutMapping("/{routeId}/name")
    @Operation(summary = "내 경로 이름 수정", description = "경로를 수정한다.")
    public void updateRouteName(@PathVariable Long routeId, @RequestBody CreateRouteNameUpdateRequest createRouteNameUpdateRequest, @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        routeService.updateRouteName(routeId, createRouteNameUpdateRequest, apiMember);
    }

    @PutMapping("/{routeId}")
    @Operation(summary = "내 경로 관광지 수정", description = "경로 관광지를 수정한다.")
    public void updateRouteSpot(@PathVariable Long routeId, @RequestBody CreateRouteSpotUpdateRequest createRouteSpotUpdateRequest, @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        routeService.updateRouteSpots(routeId, createRouteSpotUpdateRequest, apiMember);
    }

    @DeleteMapping("/{routeId}")
    @Operation(summary = "내 경로 삭제", description = "경로를 삭제한다.")
    public void deleteRoute(@PathVariable Long routeId, @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        routeService.deleteRoute(routeId, apiMember);
    }

}
