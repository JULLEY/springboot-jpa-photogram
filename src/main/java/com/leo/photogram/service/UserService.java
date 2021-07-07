package com.leo.photogram.service;

import com.leo.photogram.domain.subscribe.SubscribeRepository;
import com.leo.photogram.domain.user.User;
import com.leo.photogram.domain.user.UserRepository;
import com.leo.photogram.handler.ex.CustomException;
import com.leo.photogram.handler.ex.CustomValidationApiException;
import com.leo.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public User updateProfileImage(int principalId, MultipartFile profileImageFile){
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        try{
            Files.write(imageFilePath, profileImageFile.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(()->{
           throw new CustomValidationApiException("유저를 찾을 수 없습니다.");
        });
        userEntity.setProfileImageUrl(imageFileName);

        return userEntity;
    }

    @Transactional(readOnly = true)
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
