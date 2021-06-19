package com.leo.photogram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    // JPA query Method
    User findByUsername(String username);

}
