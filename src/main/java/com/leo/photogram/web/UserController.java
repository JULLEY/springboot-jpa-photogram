package com.leo.photogram.web;

import com.leo.photogram.config.auth.PrincipalDetails;
import com.leo.photogram.domain.user.User;
import com.leo.photogram.service.UserService;
import com.leo.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserProfileDto userProfileDto = userService.userProfile(pageUserId, principalDetails.getUser().getId());
        model.addAttribute("userInfo", userProfileDto);
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails){

//        System.out.println("session info > " + principalDetails.getUser());

        return "user/update";
    }
}
