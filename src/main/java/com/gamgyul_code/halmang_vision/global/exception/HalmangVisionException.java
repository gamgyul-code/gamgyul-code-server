package com.gamgyul_code.halmang_vision.global.exception;

import lombok.Getter;

@Getter
public class HalmangVisionException extends RuntimeException {

    private final ErrorCode errorCode;

    public HalmangVisionException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
