package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.UserProfileDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.UserProfileMapper;
import com.appliaction.justAchieveVirtualAssistant.business.UserProfileService;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class UserProfileController {

    private UserProfileService userProfileService;
    private UserProfileMapper userProfileMapper;

    @GetMapping
    public String profile(
            Model model,
            Principal principal) {
        String username = principal.getName();
        UserProfile userProfile = userProfileService.findByUsername(username);
        UserProfileDTO userProfileDTO = userProfileMapper.map(userProfile);

        model.addAttribute("userProfile", userProfileDTO);
        return "profile";
    }
}
