package com.leo.photogram.service;

import com.leo.photogram.config.auth.PrincipalDetails;
import com.leo.photogram.domain.image.Image;
import com.leo.photogram.domain.image.ImageRepository;
import com.leo.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public Page<Image> getImageStory(int principalId, Pageable pageable){
        Page<Image> images = imageRepository.mStory(principalId, pageable);
        return images;
    }

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void uploadImage(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
        System.out.println("image filename > " + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        try{
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

        // image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        Image imageEntity = imageRepository.save(image);

        System.out.println(imageEntity);
    }
}
