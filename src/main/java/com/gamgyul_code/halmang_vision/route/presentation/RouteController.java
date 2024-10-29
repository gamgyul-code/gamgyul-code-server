package com.gamgyul_code.halmang_vision.route.presentation;

import com.gamgyul_code.halmang_vision.global.utils.AuthPrincipal;
import com.gamgyul_code.halmang_vision.member.dto.ApiMember;
import com.gamgyul_code.halmang_vision.route.application.RouteService;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.CreateRouteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
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
}
