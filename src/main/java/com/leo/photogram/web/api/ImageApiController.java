package com.leo.photogram.web.api;

import com.leo.photogram.config.auth.PrincipalDetails;
import com.leo.photogram.domain.image.Image;
import com.leo.photogram.service.ImageService;
import com.leo.photogram.service.LikesService;
import com.leo.photogram.web.dto.CommRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;

    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @PageableDefault(size = 1) Pageable pageable){
        Page<Image> images = imageService.getImageStory(principalDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(new CommRespDto<>(1, "이미지 조회 성공", images), HttpStatus.OK);
    }

    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> likes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        likesService.like(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CommRespDto<>(1, "좋아요 성공", null), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> unLikes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        likesService.unLike(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CommRespDto<>(1, "좋아요 취소 성공", null), HttpStatus.OK);
    }
}
