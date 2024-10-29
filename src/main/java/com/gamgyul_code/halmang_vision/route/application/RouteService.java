package com.gamgyul_code.halmang_vision.route.application;

import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.ALREADY_EXIST_ROUTE_NAME;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.INVALID_ROUTE_SPOT_SIZE;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.INVALID_ROUT_SPOT_ID;

import com.gamgyul_code.halmang_vision.global.exception.HalmangVisionException;
import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.member.domain.MemberRepository;
import com.gamgyul_code.halmang_vision.member.dto.ApiMember;
import com.gamgyul_code.halmang_vision.route.domain.Route;
import com.gamgyul_code.halmang_vision.route.domain.RouteRepository;
import com.gamgyul_code.halmang_vision.route.domain.RouteSpot;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.CreateRouteRequest;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.CreateRouteSpotRequest;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.MyRouteResponse;
import com.gamgyul_code.halmang_vision.spot.domain.Spot;
import com.gamgyul_code.halmang_vision.spot.domain.SpotRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RouteService {

    private final MemberRepository memberRepository;
    private final SpotRepository spotRepository;
    private final RouteRepository routeRepository;

    private static final int MINIMUM_ROUTE_SPOT_SIZE = 2;
    private static final int MAXIMUM_ROUTE_SPOT_SIZE = 6;

    public void createRoute(CreateRouteRequest createRouteRequest, ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);

        String routeName = createRouteRequest.getRouteName();
        if (routeRepository.existsByRouteNameAndMember(routeName, member)) {
            throw new HalmangVisionException(ALREADY_EXIST_ROUTE_NAME);
        }

        List<Long> spotIds = createRouteRequest.getRouteSpots();
        if (spotIds.size() < MINIMUM_ROUTE_SPOT_SIZE || spotIds.size() > MAXIMUM_ROUTE_SPOT_SIZE ) {
            throw new HalmangVisionException(INVALID_ROUTE_SPOT_SIZE);
        }

        List<Spot> spots = spotRepository.findAllById(spotIds);
        if (spots.size() != spotIds.size()) {
            throw new HalmangVisionException(INVALID_ROUT_SPOT_ID);
        }

        Route route = createRouteRequest.toEntity(member);
        routeRepository.save(route);

        List<RouteSpot> routeSpots = spots.stream()
                .map(spot -> CreateRouteSpotRequest.toEntity(spot, route))
                .toList();

        route.updateRoute(routeSpots);
    }

    public List<MyRouteResponse> findAllMyRoutes(ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);

        List<Route> routes = routeRepository.findAllByMember(member);

        return routes.stream()
                .map(MyRouteResponse::fromEntity)
                .toList();
    }
}
