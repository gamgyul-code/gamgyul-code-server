package com.gamgyul_code.halmang_vision.route.application;

import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.ALREADY_EXIST_ROUTE_NAME;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.INVALID_ROUTE_SPOT_SIZE;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.INVALID_ROUT_SPOT_ID;
import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.NOT_FOUND_ROUTE;

import com.gamgyul_code.halmang_vision.bookmark.domain.BookmarkRoute;
import com.gamgyul_code.halmang_vision.bookmark.domain.BookmarkRouteRepository;
import com.gamgyul_code.halmang_vision.global.exception.HalmangVisionException;
import com.gamgyul_code.halmang_vision.member.domain.Member;
import com.gamgyul_code.halmang_vision.member.domain.MemberRepository;
import com.gamgyul_code.halmang_vision.member.dto.ApiMember;
import com.gamgyul_code.halmang_vision.route.domain.RecommendRoute;
import com.gamgyul_code.halmang_vision.route.domain.RecommendRouteRepository;
import com.gamgyul_code.halmang_vision.route.domain.Route;
import com.gamgyul_code.halmang_vision.route.domain.RouteRepository;
import com.gamgyul_code.halmang_vision.route.domain.RouteSpot;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.CreateRecommendRouteRequest;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.CreateRouteNameUpdateRequest;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.CreateRouteRequest;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.CreateRouteSpotRequest;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.CreateRouteSpotUpdateRequest;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.MyRouteDetailResponse;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.RouteResponse;
import com.gamgyul_code.halmang_vision.route.dto.RouteDto.RouteSpotResponse;
import com.gamgyul_code.halmang_vision.spot.domain.LanguageCode;
import com.gamgyul_code.halmang_vision.spot.domain.Spot;
import com.gamgyul_code.halmang_vision.spot.domain.SpotRepository;
import com.gamgyul_code.halmang_vision.spot.domain.SpotTranslation;
import com.gamgyul_code.halmang_vision.spot.domain.SpotTranslationRepository;
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
    private final RecommendRouteRepository recommendRouteRepository;
    private final SpotTranslationRepository spotTranslationRepository;
    private final BookmarkRouteRepository bookmarkRouteRepository;

    private static final int MINIMUM_ROUTE_SPOT_SIZE = 2;
    private static final int MAXIMUM_ROUTE_SPOT_SIZE = 6;

    public void createRoute(CreateRouteRequest createRouteRequest, ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);

        String routeName = createRouteRequest.getRouteName();
        List<Long> spotIds = createRouteRequest.getRouteSpots();
        List<Spot> spots = spotRepository.findAllById(spotIds);

        validateRouteName(routeName, member, false);
        validateRouteSpot(spotIds);

        Route route = createRouteRequest.toEntity(member);
        routeRepository.save(route);

        List<RouteSpot> routeSpots = spots.stream()
                .map(spot -> CreateRouteSpotRequest.toEntity(spot, route))
                .toList();

        route.initRouteSpots(routeSpots);
    }

    public void createRecommendRoute(CreateRecommendRouteRequest createRecommendRouteRequest, ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);

        String routeName = createRecommendRouteRequest.getRouteName();
        List<Long> spotIds = createRecommendRouteRequest.getRouteSpots();
        List<Spot> spots = spotRepository.findAllById(spotIds);

        validateRouteName(routeName, member, true);
        validateRouteSpot(spotIds);

        RecommendRoute recommendRoute = createRecommendRouteRequest.toEntity(member);
        recommendRouteRepository.save(recommendRoute);

        List<RouteSpot> routeSpots = spots.stream()
                .map(spot -> CreateRouteSpotRequest.toEntity(spot, recommendRoute))
                .toList();

        recommendRoute.initRouteSpots(routeSpots);
    }

    public List<RouteResponse> findAllMyRoutes(ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);

        List<Route> routes = routeRepository.findAllByMember(member);

        return routes.stream()
                .map(RouteResponse::fromEntity)
                .toList();
    }

    public MyRouteDetailResponse findRouteDetail(Long routeId, ApiMember apiMember) {

        Member member = apiMember.toMember(memberRepository);
        Route route = routeRepository.findByIdAndMember(routeId, member)
                .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_ROUTE));

        List<SpotTranslation> spotTranslations = spotTranslationRepository.findByRouteIdAndLanguageCode(routeId, member.getLanguageCode());

        List<RouteSpotResponse> routeSpotResponses = spotTranslations.stream()
                .map(RouteSpotResponse::fromEntity)
                .toList();

        return MyRouteDetailResponse.fromEntity(route, routeSpotResponses);
    }

    public List<RouteResponse> findAllRecommendRoutes(ApiMember apiMember) {

        Member member = apiMember.toMember(memberRepository);
        LanguageCode languageCode = member.getLanguageCode();
        List<RecommendRoute> recommendRoutes = recommendRouteRepository.findAllByLanguageCode(languageCode);

        List<Long> bookmarkedRecommendRouteIds = bookmarkRouteRepository.findAllByMember(member)
                .stream()
                .map(bookmarkRoute -> bookmarkRoute.getRecommendRoute().getId())
                .toList();

        return recommendRoutes.stream()
                .map(recommendRoute -> RouteResponse.fromEntity(recommendRoute,
                        bookmarkedRecommendRouteIds.contains(recommendRoute.getId())))
                .toList();
    }

    public MyRouteDetailResponse findRecommendRouteDetail(Long recommendRouteId, ApiMember apiMember) {

        Member member = apiMember.toMember(memberRepository);
        RecommendRoute recommendRoute = recommendRouteRepository.findById(recommendRouteId)
                .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_ROUTE));

        List<SpotTranslation> spotTranslations = spotTranslationRepository.findByRecommendRouteIdAndLanguageCode(recommendRouteId, member.getLanguageCode());

        List<RouteSpotResponse> routeSpotResponses = spotTranslations.stream()
                .map(RouteSpotResponse::fromEntity)
                .toList();

        return MyRouteDetailResponse.fromEntity(recommendRoute, routeSpotResponses);
    }

    public List<RouteResponse> findAllMyRecommendRoutes(ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);

        List<RecommendRoute> recommendRoutes = recommendRouteRepository.findAllByMember(member);

        List<Long> bookmarkedRecommendRouteIds = bookmarkRouteRepository.findAllByMember(member)
                .stream()
                .map(bookmarkRoute -> bookmarkRoute.getRecommendRoute().getId())
                .toList();

        return recommendRoutes.stream()
                .map(recommendRoute -> RouteResponse.fromEntity(recommendRoute,
                        bookmarkedRecommendRouteIds.contains(recommendRoute.getId())))
                .toList();
    }



    private void validateRouteSpot(List<Long> spotIds) {

        if (spotIds.size() < MINIMUM_ROUTE_SPOT_SIZE || spotIds.size() > MAXIMUM_ROUTE_SPOT_SIZE ) {
            throw new HalmangVisionException(INVALID_ROUTE_SPOT_SIZE);
        }

        List<Spot> spots = spotRepository.findAllById(spotIds);
        if (spots.size() != spotIds.size()) {
            throw new HalmangVisionException(INVALID_ROUT_SPOT_ID);
        }
    }

    private void validateRouteName(String routeName, Member member, boolean isRecommendRoute) {
        if (isRecommendRoute && recommendRouteRepository.existsByRouteName(routeName)) {
            throw new HalmangVisionException(ALREADY_EXIST_ROUTE_NAME);
        }

        if (routeRepository.existsByRouteNameAndMember(routeName, member)) {
            throw new HalmangVisionException(ALREADY_EXIST_ROUTE_NAME);
        }
    }

    public void updateRouteName(Long routeId, CreateRouteNameUpdateRequest createRouteNameUpdateRequest, ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);
        Route route = routeRepository.findByIdAndMember(routeId, member)
                .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_ROUTE));

        String routeName = createRouteNameUpdateRequest.getRouteName();
        validateRouteName(routeName, member, false);

        route.updateRouteName(routeName);
    }

    public void updateRouteSpots(Long routeId, CreateRouteSpotUpdateRequest createRouteSpotUpdateRequest, ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);
        Route route = routeRepository.findByIdAndMember(routeId, member)
                .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_ROUTE));

        List<Long> spotIds = createRouteSpotUpdateRequest.getRouteSpots();
        validateRouteSpot(spotIds);

        List<Spot> spots = spotRepository.findAllById(spotIds);
        List<RouteSpot> routeSpots = spots.stream()
                .map(spot -> CreateRouteSpotRequest.toEntity(spot, route))
                .toList();

        route.updateRouteSpots(routeSpots);
    }

    public void deleteRoute(Long routeId, ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);
        Route route = routeRepository.findByIdAndMember(routeId, member)
                .orElseThrow(() -> new HalmangVisionException(NOT_FOUND_ROUTE));

        routeRepository.delete(route);
    }
}
