package com.gamgyul_code.halmang_vision.spot.domain;

import com.gamgyul_code.halmang_vision.global.utils.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class SpotTranslation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true) // TODO : 예외 처리
    @NotBlank
    private String name;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private LanguageCode languageCode;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private SpotTranslationRegion spotTranslationRegion;

    @NotBlank
    private String summary; //  TODO : xxx 관련 장소 - 로 Enum

    @NotBlank
    private String address;

    @Size(max = 50)
    @NotBlank
    private String fee;

    @Column(columnDefinition = "LONGTEXT")
    private String taleStory;

    @Column(columnDefinition = "LONGTEXT")
    private String historyStory;

    @Column(columnDefinition = "LONGTEXT")
    private String topographyStory;

    @Column(columnDefinition = "LONGTEXT")
    private String caution;

    @Column(columnDefinition = "LONGTEXT")
    @Size(max = 15)
    private String simpleExplanation;

    @ManyToOne
    @JoinColumn(name = "spot_id")
    private Spot spot;
}
