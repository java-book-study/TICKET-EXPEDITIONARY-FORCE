package com.ticket.captain.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.net.URI;

@ToString
@NoArgsConstructor
@Getter
public class ApiResponseDto<T> {
    public static final ApiResponseDto<String> DEFAULT_OK = new ApiResponseDto<>(ApiResponseCode.OK);
    public static final ApiResponseDto<String> DEFAULT_UNAUTHORIZED = new ApiResponseDto<>(ApiResponseCode.UNAUTHORIZED);

    private ApiResponseCode code;
    private String message;
    private T data;
    private URI location;

    private ApiResponseDto(ApiResponseCode status) {
        this.bindStatus(status);
    }

    private ApiResponseDto(ApiResponseCode status, T data) {
        this.bindStatus(status);
        this.data = data;
    }

    private ApiResponseDto(ApiResponseCode status, T data, URI location){
        this.bindStatus(status);
        this.data = data;
        this.location = location;
    }

    private void bindStatus(ApiResponseCode status) {
        this.code = status;
        this.message = status.getMessage();
    }

    public static <T> ApiResponseDto<T> createOK(T data) {
        return new ApiResponseDto<>(ApiResponseCode.OK, data);
    }

    public static <T> ApiResponseDto<T> NOT_FOUND(T data) {
        return new ApiResponseDto<>(ApiResponseCode.NOT_FOUND, data);
    }

    public static <T> ApiResponseDto<T> BAD_REQUEST(T data){
        return new ApiResponseDto<>(ApiResponseCode.BAD_REQUEST, data);
    }

    public static <T> ApiResponseDto<T> VALIDATION_ERROR(T data) {
        return new ApiResponseDto<>(ApiResponseCode.VALIDATION_ERROR, data);
    }

}

