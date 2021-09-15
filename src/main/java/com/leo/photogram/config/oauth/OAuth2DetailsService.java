package com.leo.photogram.config.oauth;

import com.leo.photogram.domain.user.User;
import com.leo.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        System.out.println("OAuth2 작동 =================");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> userInfo = oAuth2User.getAttributes();
        String username = "facebook_" + (String) userInfo.get("id");
        String password = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        User userEntity = userRepository.findByUsername(username);

        if(ObjectUtils.isEmpty(userEntity)){
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .name(name)
                    .build();

            return new OAuth2UserDetails(userRepository.save(user));
        }else{
            return new OAuth2UserDetails(userEntity);
        }
    }
}
