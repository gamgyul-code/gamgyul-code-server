package com.gamgyul_code.halmang_vision.spot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotCategory {
    HALMANG, LOVE, HISTORY, MYTH;

    @JsonCreator
    public static SpotCategory from(String value) {
        for (SpotCategory category : SpotCategory.values()) {
            if (category.name().equals(value)) {
                return category;
            }
        }
        throw new IllegalStateException("Unexpected value: " + value); //TODO: 커스텀 에러 구현
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
