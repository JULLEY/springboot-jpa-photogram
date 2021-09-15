package com.leo.photogram.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// NotNull = Null 체크
// NotEmpty 빈값이거나 null 체크
// NotBlank 빈값이거나 null, 빈 공백까지 체크

@Data
public class CommentDto {
    @NotBlank
    private String content;
    @NotNull
    private Integer imageId;
}
