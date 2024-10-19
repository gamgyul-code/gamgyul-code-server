package com.gamgyul_code.halmang_vision.spot.domain;

import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.INVALID_SPOT_CATEGORY;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.gamgyul_code.halmang_vision.global.exception.HalmangVisionException;

public enum SpotRegion {

    WESTERN_JEJU_CITY, JEJU_CITY, EASTERN_JEJU_CITY, WESTERN_SEOGWIPO_CITY, SEOGWIPO_CITY, EASTERN_SEOGWIPO_CITY;

    @JsonCreator
    public static SpotRegion from(String value) {
        for (SpotRegion spotRegion : SpotRegion.values()) {
            if (spotRegion.name().equals(value)) {
                return spotRegion;
            }
        }
        throw new HalmangVisionException(INVALID_SPOT_CATEGORY); //TODO: 커스텀 에러 구현
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
