package com.gamgyul_code.halmang_vision.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 Bad Request
    FILE_SIZE_EXCEEDED(BAD_REQUEST, "파일 크기가 초과되었습니다."),
    INVALID_LANGUAGE_CODE(BAD_REQUEST, "유효하지 않은 언어 코드입니다."),
    INVALID_TRAVELER_STATISTICS(BAD_REQUEST, "유효하지 않은 여행자 통계입니다."),
    INVALID_SPOT_CATEGORY(BAD_REQUEST, "유효하지 않은 관광지 카테고리입니다."),
    INVALID_SPOT_REGION(BAD_REQUEST, "유효하지 않은 관광지 지역입니다."),
    INVALID_SPOT_TRANSLATION_REGION(BAD_REQUEST, "유효하지 않은 관광지 번역 지역입니다."),
    // 401 Unauthorized

    // 403 Forbidden

    // 404 Not Found
    NOT_FOUND_MEMBER(NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    NOT_FOUND_SPOT(NOT_FOUND, "해당 관광지를 찾을 수 없습니다."),
    NOT_FOUND_SPOT_TRANSLATION(NOT_FOUND, "해당 관광지 번역을 찾을 수 없습니다."),
    NOT_FOUND_BOOKMARK(NOT_FOUND, "해당 북마크를 찾을 수 없습니다."),

    // 409 Conflict
    ALREADY_EXIST_SPOT(CONFLICT, "이미 존재하는 관광지입니다."),
    ALREADY_EXIST_SPOT_TRANSLATION(CONFLICT, "이미 존재하는 관광지 번역입니다."),
    ALREADY_EXIST_SPOT_TRANSLATION_NAME(CONFLICT, "이미 존재하는 번역된 관광지 이름입니다."),
    ALREADY_BOOKMARKED(CONFLICT, "이미 저장한 관광지입니다.");

    // 500 Internal Server Error

    private final HttpStatus httpStatus;
    private final String message;
}
