package com.leo.photogram.web;

import com.leo.photogram.domain.user.User;
import com.leo.photogram.handler.ex.CustomValidationException;
import com.leo.photogram.service.AuthService;
import com.leo.photogram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @GetMapping("/auth/signin")
    public String signinForm(){
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(){
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult){
        User user = signupDto.toEntity();
        User userEntity = authService.signUp(user);
        return "auth/signin";
    }
}
