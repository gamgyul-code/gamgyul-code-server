package com.gamgyul_code.halmang_vision.spot.domain;

import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.INVALID_SPOT_CATEGORY;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.gamgyul_code.halmang_vision.global.exception.HalmangVisionException;

public enum SpotCategory {

    halmang, love, history, myth;

    @JsonCreator
    public static SpotCategory from(String value) {
        for (SpotCategory category : SpotCategory.values()) {
            if (category.name().equals(value)) {
                return category;
            }
        }
        throw new HalmangVisionException(INVALID_SPOT_CATEGORY); //TODO: 커스텀 에러 구현
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
