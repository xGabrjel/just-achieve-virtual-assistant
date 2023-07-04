package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.UserProfileDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.UserProfileMapper;
import com.appliaction.justAchieveVirtualAssistant.business.UserProfileService;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;


@Controller
@RequestMapping("/user-profile")
@AllArgsConstructor
public class UserProfileController {

    private UserProfileService userProfileService;
    private UserProfileMapper userProfileMapper;

    @GetMapping
    public String homeProfilePage() {
        return "user-profile";
    }

    @GetMapping("/get-data")
    public String profilePage(
            Model model,
            Principal principal
    ) {
        String username = principal.getName();
        UserProfile userProfile = userProfileService.findByUsername(username);
        UserProfileDTO userProfileDTO = userProfileMapper.map(userProfile);

        model.addAttribute("userProfile", userProfileDTO);
        return "user-profile";
    }

    @PostMapping("/submit-user-profile-data")
    public String submitProfileData(
            Model model,
            Principal principal,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "surname", required = true) String surname,
            @RequestParam(value = "phone", required = true) String phone,
            @RequestParam(value = "age", required = true) Integer age,
            @RequestParam(value = "sex", required = true) String sex,
            @RequestParam(value = "weight", required = true) BigDecimal weight,
            @RequestParam(value = "height", required = true) BigDecimal height,
            @RequestParam(value = "dietGoalsId", required = true) Integer dietGoalsId
    ) {
        String username = principal.getName();
        userProfileService.saveUserProfileData(username, name, surname, phone, age, sex, weight, height, dietGoalsId);
        return "redirect:/user-profile?success";
    }

}
