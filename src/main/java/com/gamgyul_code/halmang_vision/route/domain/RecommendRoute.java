package com.gamgyul_code.halmang_vision.route.domain;

import com.gamgyul_code.halmang_vision.global.utils.BaseTimeEntity;
import com.gamgyul_code.halmang_vision.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendRoute extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 15)
    @Column(unique = true)
    private String routeName;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "recommendRoute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RouteSpot> recommendRouteSpots = new ArrayList<>();

    private String imgUrl;

    public void updateRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void initRouteSpots(List<RouteSpot> routeSpots) {
        this.recommendRouteSpots = routeSpots;
    }

    public void updateRouteSpots(List<RouteSpot> routeSpots) {
        this.recommendRouteSpots.clear();
        this.recommendRouteSpots.addAll(routeSpots);
    }
}
