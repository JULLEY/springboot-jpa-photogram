package com.leo.photogram.service;

import com.leo.photogram.domain.user.User;
import com.leo.photogram.domain.user.UserRepository;
import com.leo.photogram.handler.ex.CustomException;
import com.leo.photogram.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User userProfile(int userId){
        User userEntity = userRepository.findById(userId).orElseThrow(()->{
            throw new CustomException("존재하지 않는 페이지입니다.");
        });
        userEntity.getImages();
        return userEntity;
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
