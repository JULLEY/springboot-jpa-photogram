package com.leo.photogram.service;

import com.leo.photogram.domain.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public void uploadImage(){

    }
}
