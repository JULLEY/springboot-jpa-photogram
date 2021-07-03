package com.leo.photogram.service;

import com.leo.photogram.domain.subscribe.SubscribeRepository;
import com.leo.photogram.domain.user.User;
import com.leo.photogram.domain.user.UserRepository;
import com.leo.photogram.handler.ex.CustomException;
import com.leo.photogram.handler.ex.CustomValidationApiException;
import com.leo.photogram.web.dto.user.UserProfileDto;
import com.nimbusds.jose.util.IntegerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserProfileDto userProfile(int pageUserId, int principalId){

        UserProfileDto userProfileDto = new UserProfileDto();


        User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
            throw new CustomException("존재하지 않는 페이지입니다.");
        });

        userProfileDto.setUser(userEntity);
        userProfileDto.setPageOwnerState(pageUserId == principalId);    // true 주인 , false 주인 아님
        userProfileDto.setImageCount(userEntity.getImages().size());

        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        userProfileDto.setSubscribeState(subscribeState == 1);
        userProfileDto.setSubscribeCount(subscribeCount);

        // 프로필 좋아요 카운트조회
        userEntity.getImages().forEach((image) -> {
            image.setLikeCount(image.getLikes().size());
        });

        return userProfileDto;
    }

    @Transactional
    public User updateUserInfo(int id, User user){
        // 영속화 및 영속화된 오브젝트 수정
        // 1. 무조건 찾았다. get() 2. 못찾았다. orElseThrow() 발동
        User userEntity = userRepository.findById(id).orElseThrow(() -> {return new CustomValidationApiException("찾을 수 없는 ID 입니다.");});
        userEntity.setName(user.getName());

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);

        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    }

}
