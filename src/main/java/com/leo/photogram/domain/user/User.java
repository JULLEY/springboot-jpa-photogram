package com.leo.photogram.domain.user;

// JPA - Java Persistance API (자바로 데이터를 영구적으로 저장할 수 있는 API를 제공)

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leo.photogram.domain.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략을 데이터베이스를 따라간다.
    private int id;

    @Column(length = 100, unique = true)    // OAuth2 로그인을위해 100자로 늘림
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String website;
    private String bio;

    @Column(nullable = false)
    private String email;

    private String phone;
    private String gender;

    private String profileImageUrl; // 프로필 사진
    private String role;            // 권한

    // mappedBy > 나는 연관관계의 주인이 아니다. 해당 컬럼생성을 막는다.
    // User를 select 할 때 해당 User id로 등록된 image들을 다 가져오라는 의미.
    // FetchType.LAZY는 User를 select 할 때 해당 User에 등록된 image를 가져오지 말라는 의미. - 대신 getImages() 함수가 호출될 때 가져오라는 의미.
    // FetchType.EAGER는 User를 select 할 때 해당 User에 등록된 image를 전부 JOIN해서 가져오라는 의미.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"}) // Image안에있는 user를 ignore시킨다
    private List<Image> images;

    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT되기 직전 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", role='" + role + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
