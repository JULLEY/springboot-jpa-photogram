package com.leo.photogram.handler;

import com.leo.photogram.handler.ex.CustomValidationException;
import com.leo.photogram.util.Script;
import com.leo.photogram.web.dto.CommRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
        return Script.goBack(e.getErrorMap().toString());
    }

//    @ExceptionHandler(CustomValidationException.class)
//    public CommRespDto<?> validationException(CustomValidationException e){
//        return new CommRespDto<Map<String,Object>>(9999 ,e.getMessage(), e.getErrorMap());
//    }

}
