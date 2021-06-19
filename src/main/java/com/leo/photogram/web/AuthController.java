package com.leo.photogram.web;

import com.leo.photogram.domain.user.User;
import com.leo.photogram.service.AuthService;
import com.leo.photogram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.build.ToStringPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
//    @Autowired
//    private AuthService authService;

    @GetMapping("/auth/signin")
    public String signinForm(){
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(){
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(SignupDto signupDto){
        log.info(signupDto.toString());
        User user = signupDto.toEntity();
        User res = authService.signUp(user);
        log.info("회원가입결과 > " + res.toString());
        return "auth/signin";
    }
}
