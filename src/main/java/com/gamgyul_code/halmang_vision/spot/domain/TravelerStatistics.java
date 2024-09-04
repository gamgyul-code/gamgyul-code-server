package com.gamgyul_code.halmang_vision.spot.domain;

import static com.gamgyul_code.halmang_vision.global.exception.ErrorCode.INVALID_TRAVELER_STATISTICS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.gamgyul_code.halmang_vision.global.exception.HalmangVisionException;

public enum TravelerStatistics {
    HIGH, MEDIUM, LOW;

    @JsonCreator
    public static TravelerStatistics from(String value) {
        for (TravelerStatistics statistics : TravelerStatistics.values()) {
            if (statistics.name().equals(value)) {
                return statistics;
            }
        }
        throw new HalmangVisionException(INVALID_TRAVELER_STATISTICS);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
