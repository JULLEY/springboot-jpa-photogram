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
    public List<Image> popularImageList(){
        return imageRepository.mPopular();
    }

    @Transactional(readOnly = true)
    public Page<Image> getImageStory(int principalId, Pageable pageable){
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        // images에 좋아요 상태 담기
        images.forEach((image)->{

            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((like) -> {
                if(like.getUser().getId() == principalId) { // 해당 이미지에 좋아요한 사람들을 찾아서 현재 로긴한 사람이 좋아요 한것인지 비교
                    image.setLikeState(true);
                }
            });

        });

        return images;
    }

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void uploadImage(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID(); // uuid
        String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); // 1.jpg
        System.out.println("이미지 파일이름 : "+imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        // 통신, I/O -> 예외가 발생할 수 있다.
        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser()); // 5cf6237d-c404-43e5-836b-e55413ed0e49_bag.jpeg
        imageRepository.save(image);
    }
}
