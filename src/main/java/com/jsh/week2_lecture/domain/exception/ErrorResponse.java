package com.jsh.week2_lecture.domain.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String code, String message, HttpStatus status) {

    public static final ErrorResponse LECTURE_FULL = new ErrorResponse("LECTURE_FULL", "해당 강의는 정원이 초과되어 더 이상 신청이 불가합니다",HttpStatus.NOT_FOUND);
    public static final ErrorResponse LECTURE_NOT_FOUND = new ErrorResponse("LECTURE_NOT_FOUND", "존재하지 않는 강의입니다.",HttpStatus.CONFLICT);
    public static final ErrorResponse DUPLICATE_APPLICATION = new ErrorResponse("DUPLICATE_APPLICATION", "사용자가 이미 신청한 강의입니다.",HttpStatus.CONFLICT);
    public static final ErrorResponse USER_NOT_FOUND = new ErrorResponse("USER_NOT_FOUDN","존재하지 않는 사용자입니다.",HttpStatus.NOT_FOUND);
}
