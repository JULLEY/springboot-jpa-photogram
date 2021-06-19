package com.leo.photogram.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommRespDto<T> {

    private int resultCode;   // 1 성공 , 9999 실패
    private String message;
    private Map<String,Object> errorMap;
}
