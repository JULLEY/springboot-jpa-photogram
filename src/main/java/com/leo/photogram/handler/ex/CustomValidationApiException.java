package com.leo.photogram.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException{

    private Map<String, Object> errorMap;

    public CustomValidationApiException(String message){
        super(message);
    }
    public CustomValidationApiException(String message, Map<String,Object> errorMap){
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, Object> getErrorMap() {
        return errorMap;
    }
}
