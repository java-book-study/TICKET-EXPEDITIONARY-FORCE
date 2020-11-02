package com.ticket.captain.response;

import com.ticket.captain.utils.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode implements EnumType {
    OK("요청이 성공하였습니다.", 200),
    BAD_REQUEST("잘못된 요청.", 400),
    NOT_FOUND("리소스를 찾지 못했습니다.", 404),
    UNAUTHORIZED("인증에 실패하였습니다.",401),
    SERVER_ERROR("서버 에러입니다.",500);


    private final String message;

    @Override
    public String getId() {
        return name();
    }

    @Override
    public String getText() {
        return message;
    }
}