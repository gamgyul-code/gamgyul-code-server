package com.gamgyul_code.halmang_vision.spot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TravelerStatistics {
    HIGH, MEDIUM, LOW;

    @JsonCreator
    public static TravelerStatistics from(String value) {
        for (TravelerStatistics statistics : TravelerStatistics.values()) {
            if (statistics.name().equals(value)) {
                return statistics;
            }
        }
        throw new IllegalStateException("Unexpected value: " + value); //TODO: 커스텀 에러 구현
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
