package com.leo.photogram.service;

import com.leo.photogram.domain.comment.Comment;
import com.leo.photogram.domain.comment.CommentRepository;
import com.leo.photogram.domain.image.Image;
import com.leo.photogram.domain.user.User;
import com.leo.photogram.domain.user.UserRepository;
import com.leo.photogram.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 댓글 작성
    @Transactional
    public Comment saveComment(String content, int imageId, int userId){

        // Tip 객체 만들 때 id값만 담아서 insert시키는 방법
        // 대신 return 시에 image, user객체는 id값만 가지고 있는 빈 객체를 리턴한다
        Image image = new Image();
        image.setId(imageId);

        User userEntity = userRepository.findById(userId).orElseThrow(()->{
            throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
        });

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(userEntity);

        return commentRepository.save(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteCommnet(int id){
        try {
            commentRepository.deleteById(id);
        }catch(Exception e){
            throw new CustomApiException(e.getMessage());
        }
    }
}
