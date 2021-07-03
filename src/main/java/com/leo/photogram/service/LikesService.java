package com.leo.photogram.service;

import com.leo.photogram.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;

    @Transactional
    public void like(int imageId, int principalId){
        likesRepository.mLikes(imageId, principalId);
    }

    @Transactional
    public void unLike(int imageId, int principalId){
        likesRepository.mUnLikes(imageId, principalId);
    }
}
