package com.leo.photogram.web.api;

import com.leo.photogram.config.auth.PrincipalDetails;
import com.leo.photogram.service.SubscribeService;
import com.leo.photogram.web.dto.CommRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {

    private final SubscribeService subscribeService;

    @PostMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId){
        subscribeService.subscribe(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new CommRespDto<>(1, "구독하기 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId){
        subscribeService.unSubscribe(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new CommRespDto<>(1, "구독취소 성공", null), HttpStatus.OK);
    }
}
