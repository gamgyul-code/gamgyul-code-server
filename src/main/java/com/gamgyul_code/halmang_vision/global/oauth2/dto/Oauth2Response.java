package com.gamgyul_code.halmang_vision.global.oauth2.dto;

public interface Oauth2Response {

    //제공자 (Ex. naver, google, ...)
    String getProvider();

    //제공자에서 발급해주는 아이디(번호)
    String getProviderId();

    //이메일
    String getEmail();
}
