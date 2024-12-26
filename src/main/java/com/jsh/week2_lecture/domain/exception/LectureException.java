package com.jsh.week2_lecture.domain.exception;

import org.springframework.http.HttpStatus;

public class LectureException extends RuntimeException{

    private final ErrorResponse errorResponse;

    public LectureException(ErrorResponse errorResponse) {
        super(errorResponse.message());
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public HttpStatus getStatus() {
        return errorResponse.status();
    }
}
