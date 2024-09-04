package com.gamgyul_code.halmang_vision.spot.domain;

import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.INVALID_LANGUAGE_CODE;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.gamgyul_code.halmang_vision.global.exception.HalmangVisionException;

public enum LanguageCode {
    KOR, ENG, JPN, CHN;

    @JsonCreator
    public static LanguageCode from(String value) {
        for (LanguageCode code : LanguageCode.values()) {
            if (code.name().equals(value)) {
                return code;
            }
        }
        throw new HalmangVisionException(INVALID_LANGUAGE_CODE);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
