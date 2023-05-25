package com.hanex.starter.error.exception;


import com.hanex.starter.common.api.ApiResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 인증 안됨
@Getter
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public ApiResponseDto<?> body(){
        return new ApiResponseDto<>(HttpStatus.UNAUTHORIZED, "unAuthorized", getMessage());
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}
