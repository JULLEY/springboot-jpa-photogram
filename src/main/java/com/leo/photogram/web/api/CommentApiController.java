package com.leo.photogram.web.api;

import com.leo.photogram.config.auth.PrincipalDetails;
import com.leo.photogram.domain.comment.Comment;
import com.leo.photogram.handler.ex.CustomValidationApiException;
import com.leo.photogram.service.CommentService;
import com.leo.photogram.web.dto.CommRespDto;
import com.leo.photogram.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> saveComment(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Comment comment = commentService.saveComment(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId());
        return new ResponseEntity<>(new CommRespDto<>(1, "댓글쓰기성공", comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable int id){
        commentService.deleteCommnet(id);
        return new ResponseEntity<>(new CommRespDto<>(1, "댓글삭제성공", null), HttpStatus.OK);
    }
}
