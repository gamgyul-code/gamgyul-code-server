package com.gamgyul_code.halmang_vision.spot.domain;

import com.gamgyul_code.halmang_vision.bookmark.domain.Bookmark;
import com.gamgyul_code.halmang_vision.global.utils.BaseTimeEntity;
import com.gamgyul_code.halmang_vision.route.domain.RouteSpot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class Spot extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private SpotCategory spotCategory; // TODO : 리스트로 만들어야 함. 교집합 카테고리 가능

    private String imgUrl;

    @Enumerated(value = EnumType.STRING)
    private TravelerStatistics travelerStatistics;

    @Size(max = 50)
    @NotBlank
    private String openingHours;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
    private String phoneNumber;

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpotTranslation> translations = new ArrayList<>();

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RouteSpot> routeSpots = new ArrayList<>();
}
