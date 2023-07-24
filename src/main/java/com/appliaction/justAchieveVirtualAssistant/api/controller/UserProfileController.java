package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.UserProfileDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.UserProfileMapper;
import com.appliaction.justAchieveVirtualAssistant.business.DietGoalsService;
import com.appliaction.justAchieveVirtualAssistant.business.ImagesService;
import com.appliaction.justAchieveVirtualAssistant.business.UserProfileService;
import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;


@Controller
@AllArgsConstructor
@RequestMapping("/user-profile")
public class UserProfileController {

    private UserProfileService userProfileService;
    private UserProfileMapper userProfileMapper;
    private DietGoalsService dietGoalsService;
    private UserService userService;
    private ImagesService imagesService;

    @GetMapping
    public String homeProfilePage() {
        return "user-profile";
    }

    @GetMapping("/data")
    public String profilePage(
            Model model,
            Principal principal
    ) {
        String username = principal.getName();
        UserProfile userProfile = userProfileService.findByUsername(username);
        UserProfileDTO userProfileDTO = userProfileMapper.map(userProfile);

        byte[] bytes = imagesService.downloadImageByProfileId(username);
        String imageBase64 = Base64.getEncoder().encodeToString(bytes);

        model.addAttribute("userProfile", userProfileDTO);
        model.addAttribute("imageBase64", imageBase64);
        return "user-profile";
    }

    @PostMapping("/new-user-profile-data")
    public String submitProfileData(
            Principal principal,
            Integer dietGoalId,
            @Valid @ModelAttribute("userProfileDTO") UserProfileDTO userProfileDTO,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
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

        userProfileService.saveUserProfileData(username, userProfile, file);
        return "redirect:/user-profile?success";
    }
}
