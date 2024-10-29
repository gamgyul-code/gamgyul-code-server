package com.gamgyul_code.halmang_vision.route.domain;

import com.gamgyul_code.halmang_vision.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {

    boolean existsByRouteNameAndMember(String routeName, Member member);
}
