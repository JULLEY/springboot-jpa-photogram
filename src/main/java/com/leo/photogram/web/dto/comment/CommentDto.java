package com.leo.photogram.web.dto.comment;

import lombok.Data;

@Data
public class CommentDto {
    private String content;
    private int imageId;
}
