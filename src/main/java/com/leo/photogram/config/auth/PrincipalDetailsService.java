package com.leo.photogram.config.auth;

import com.leo.photogram.domain.user.User;
import com.leo.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 패스워드 알아서 체크해준다.
    // 리턴성공 시 자동으로 세션생성
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);

        if(ObjectUtils.isEmpty(userEntity)){
            return null;
        }else{
            return new PrincipalDetails(userEntity);
        }

    }
}
