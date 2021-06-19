package com.leo.photogram.handler;

import com.leo.photogram.handler.ex.CustomValidationApiException;
import com.leo.photogram.handler.ex.CustomValidationException;
import com.leo.photogram.util.Script;
import com.leo.photogram.web.dto.CommRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    // 클라이언트에게 응답하는 경우
    // script 반환
    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
        return Script.goBack(e.getErrorMap().toString());
    }

    // Ajax통신용
    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e){
        return new ResponseEntity<>(new CommRespDto<>(9999, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }
}
