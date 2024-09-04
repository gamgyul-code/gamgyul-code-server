package com.gamgyul_code.halmang_vision.spot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LanguageCode {
    KOR, ENG, JPN, CHN;

    @JsonCreator
    public static LanguageCode from(String value) {
        for (LanguageCode code : LanguageCode.values()) {
            if (code.name().equals(value)) {
                return code;
            }
        }
        throw new IllegalStateException("Unexpected value: " + value); //TODO: 커스텀 에러 구현
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
