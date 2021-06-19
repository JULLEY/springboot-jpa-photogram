package com.leo.photogram.service;

import com.leo.photogram.domain.subscribe.SubscribeRepository;
import com.leo.photogram.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void subscribe(int fromUserId, int toUserId){
        try {
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        } catch (Exception e){
            throw new CustomApiException("이미 구독중인 사용자입니다.");
        }
    }

    @Transactional
    public void unSubscribe(int fromUserId, int toUserId){
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }
}
