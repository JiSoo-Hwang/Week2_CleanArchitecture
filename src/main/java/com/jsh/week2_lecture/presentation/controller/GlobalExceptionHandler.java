package com.jsh.week2_lecture.presentation.controller;

import com.jsh.week2_lecture.domain.exception.ErrorResponse;
import com.jsh.week2_lecture.domain.exception.LectureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //정의해놓은 강의 관련 에러 처리
    @ExceptionHandler(LectureException.class)
    public ResponseEntity<ErrorResponse>handleLectureException(LectureException ex){
        ErrorResponse errorResponse = ex.getErrorResponse();
        return ResponseEntity.status(errorResponse.status())
                .body(errorResponse);
    }
    
    //기타 RuntimeException 처리
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse>handleRuntimeException(RuntimeException ex){
        ErrorResponse defaultError = new ErrorResponse(
          "INTERNAL_SERVER_ERROR",
                "서버에서 문제가 발생했습니다.",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(defaultError);
    }
}
