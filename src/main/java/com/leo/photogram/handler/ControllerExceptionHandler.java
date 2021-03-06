package com.leo.photogram.handler;

import com.leo.photogram.handler.ex.CustomApiException;
import com.leo.photogram.handler.ex.CustomException;
import com.leo.photogram.handler.ex.CustomValidationApiException;
import com.leo.photogram.handler.ex.CustomValidationException;
import com.leo.photogram.util.Script;
import com.leo.photogram.web.dto.CommRespDto;
import com.nimbusds.oauth2.sdk.util.MapUtils;
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
        if(MapUtils.isEmpty(e.getErrorMap())){
            return Script.goBack(e.getMessage());
        }else{
            return Script.goBack(e.getErrorMap().toString());
        }
    }

    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e) {
        return Script.goBack(e.getMessage());
    }

    // Ajax통신용
    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e){
        return new ResponseEntity<>(new CommRespDto<>(9999, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }
    // Ajax통신용
    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e){
        return new ResponseEntity<>(new CommRespDto<>(9999, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
