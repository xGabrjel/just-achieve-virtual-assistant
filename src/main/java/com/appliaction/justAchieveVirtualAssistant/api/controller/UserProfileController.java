package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.UserProfileDTO;
import com.appliaction.justAchieveVirtualAssistant.business.ImagesService;
import com.appliaction.justAchieveVirtualAssistant.business.UserProfileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

import static com.appliaction.justAchieveVirtualAssistant.api.controller.UserProfileController.ROOT;


@Controller
@AllArgsConstructor
@RequestMapping(ROOT)
public class UserProfileController {

    static final String ROOT = "/user-profile";
    static final String PROFILE = "/data";
    static final String SUBMIT_PROFILE_DATA = "/new-user-profile-data";

    private UserProfileService userProfileService;
    private ImagesService imagesService;

    @GetMapping
    public String homeProfilePage() {
        return "user-profile";
    }

    @GetMapping(PROFILE)
    public String profilePage(
            Model model,
            Principal principal
    ) {
        String username = principal.getName();
        model.addAttribute("userProfile", userProfileService.findDTOByUsername(username));
        model.addAttribute("imageBase64", imagesService.downloadAndConvertImage(username));
        return "user-profile";
    }

    @PostMapping(SUBMIT_PROFILE_DATA)
    public String submitProfileData(
            Principal principal,
            Integer dietGoalId,
            @Valid @ModelAttribute("userProfileDTO") UserProfileDTO userProfileDTO,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        userProfileService.saveUserProfileData(principal.getName(), dietGoalId, userProfileDTO, file);
        return "redirect:/user-profile?success";
    }
}
