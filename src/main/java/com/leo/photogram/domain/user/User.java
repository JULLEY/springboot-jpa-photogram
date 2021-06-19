package com.leo.photogram.domain.user;

// JPA - Java Persistance API (자바로 데이터를 영구적으로 저장할 수 있는 API를 제공)

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;

    private String password;
    private String name;
    private String website;
    private String bio;
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl; // 프로필 사진
    private String role;            // 권한

    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT되기 직전 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }
}
