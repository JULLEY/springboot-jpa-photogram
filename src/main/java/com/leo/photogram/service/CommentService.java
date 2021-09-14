package com.leo.photogram.service;

import com.leo.photogram.domain.comment.Comment;
import com.leo.photogram.domain.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 작성
    @Transactional
    public Comment saveComment(){
        return null;
    }

    // 댓글 삭제
    @Transactional
    public void deleteCommnet(){

    }
}
