package com.gamgyul_code.halmang_vision.spot.domain;

import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.INVALID_SPOT_TRANSLATION_REGION;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.gamgyul_code.halmang_vision.global.exception.HalmangVisionException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SpotTranslationRegion {

    ENG_WESTERN_JEJU_CITY(LanguageCode.ENG, SpotRegion.WESTERN_JEJU_CITY, "Western Jeju City"),
    ENG_JEJU_CITY(LanguageCode.ENG, SpotRegion.JEJU_CITY, "Jeju City"),
    ENG_EASTERN_JEJU_CITY(LanguageCode.ENG, SpotRegion.EASTERN_JEJU_CITY, "Eastern Jeju City"),
    ENG_WESTERN_SEOGWIPO_CITY(LanguageCode.ENG, SpotRegion.WESTERN_SEOGWIPO_CITY, "Western Seogwipo City"),
    ENG_SEOGWIPO_CITY(LanguageCode.ENG, SpotRegion.SEOGWIPO_CITY, "Seogwipo City"),
    ENG_EASTERN_SEOGWIPO_CITY(LanguageCode.ENG, SpotRegion.EASTERN_SEOGWIPO_CITY, "Eastern Seogwipo City"),
    KOR_WESTERN_JEJU_CITY(LanguageCode.KOR, SpotRegion.WESTERN_JEJU_CITY, "제주시 서쪽"),
    KOR_JEJU_CITY(LanguageCode.KOR, SpotRegion.JEJU_CITY, "제주시"),
    KOR_EASTERN_JEJU_CITY(LanguageCode.KOR, SpotRegion.EASTERN_JEJU_CITY, "제주시 동쪽"),
    KOR_WESTERN_SEOGWIPO_CITY(LanguageCode.KOR, SpotRegion.WESTERN_SEOGWIPO_CITY, "서귀포시 서쪽"),
    KOR_SEOGWIPO_CITY(LanguageCode.KOR, SpotRegion.SEOGWIPO_CITY, "서귀포시"),
    KOR_EASTERN_SEOGWIPO_CITY(LanguageCode.KOR, SpotRegion.EASTERN_SEOGWIPO_CITY, "서귀포시 동쪽"),
    CHN_WESTERN_JEJU_CITY(LanguageCode.CHN, SpotRegion.WESTERN_JEJU_CITY, "濟州西部"),
    CHN_JEJU_CITY(LanguageCode.CHN, SpotRegion.JEJU_CITY, "濟州市"),
    CHN_EASTERN_JEJU_CITY(LanguageCode.CHN, SpotRegion.EASTERN_JEJU_CITY, "济州市东"),
    CHN_WESTERN_SEOGWIPO_CITY(LanguageCode.CHN, SpotRegion.WESTERN_SEOGWIPO_CITY, "西归浦市西部"),
    CHN_SEOGWIPO_CITY(LanguageCode.CHN, SpotRegion.SEOGWIPO_CITY, "西归浦市"),
    CHN_EASTERN_SEOGWIPO_CITY(LanguageCode.CHN, SpotRegion.EASTERN_SEOGWIPO_CITY, "西归浦市东部"),
    JPN_WESTERN_JEJU_CITY(LanguageCode.JPN, SpotRegion.WESTERN_JEJU_CITY, "済州西部"),
    JPN_JEJU_CITY(LanguageCode.JPN, SpotRegion.JEJU_CITY, "済州市"),
    JPN_EASTERN_JEJU_CITY(LanguageCode.JPN, SpotRegion.EASTERN_JEJU_CITY, "済州市東部"),
    JPN_WESTERN_SEOGWIPO_CITY(LanguageCode.JPN, SpotRegion.WESTERN_SEOGWIPO_CITY, "西帰浦市西部"),
    JPN_SEOGWIPO_CITY(LanguageCode.JPN, SpotRegion.SEOGWIPO_CITY, "西帰浦市"),
    JPN_EASTERN_SEOGWIPO_CITY(LanguageCode.JPN, SpotRegion.EASTERN_SEOGWIPO_CITY, "西帰浦市東部");


    private final LanguageCode languageCode;

    private final SpotRegion spotRegion;

    private final String name;

    @JsonCreator
    public static SpotTranslationRegion from(String value) {
        for (SpotTranslationRegion spotTranslationRegion : SpotTranslationRegion.values()) {
            if (spotTranslationRegion.name().equals(value)) {
                return spotTranslationRegion;
            }
        }
        throw new HalmangVisionException(INVALID_SPOT_TRANSLATION_REGION);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
