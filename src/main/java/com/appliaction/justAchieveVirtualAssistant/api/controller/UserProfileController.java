package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.UserProfileDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.UserProfileMapper;
import com.appliaction.justAchieveVirtualAssistant.business.DietGoalsService;
import com.appliaction.justAchieveVirtualAssistant.business.UserProfileService;
import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@AllArgsConstructor
@RequestMapping("/user-profile")
public class UserProfileController {

    private UserProfileService userProfileService;
    private UserProfileMapper userProfileMapper;
    private DietGoalsService dietGoalsService;
    private UserService userService;

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
            Integer dietGoalId,
            @Valid @ModelAttribute("userProfileDTO") UserProfileDTO userProfileDTO
    ) {
        String username = principal.getName();
        User user = userService.findByUsername(username).orElseThrow();
        DietGoals dietGoals = dietGoalsService.findById(dietGoalId).orElseThrow();

        UserProfile userProfile = UserProfile.builder()
                .user(user)
                .name(userProfileDTO.getName())
                .surname(userProfileDTO.getSurname())
                .phone(userProfileDTO.getPhone())
                .age(userProfileDTO.getAge())
                .sex(userProfileDTO.getSex())
                .weight(userProfileDTO.getWeight())
                .height(userProfileDTO.getHeight())
                .dietGoal(dietGoals)
                .build();

        userProfileService.saveUserProfileData(username, userProfile);
        return "redirect:/user-profile?success";
    }
}
