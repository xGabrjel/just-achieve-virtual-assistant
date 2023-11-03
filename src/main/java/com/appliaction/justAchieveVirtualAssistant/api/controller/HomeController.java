package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.business.UserProfileService;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HomeController {

    private final UserProfileService userProfileService;

    @GetMapping
    public String homePage(
            Model model,
            Principal principal
    ) {
        boolean isProfileCompleted;

        try {
            String username = principal.getName();
            UserProfile userProfile = userProfileService.findByUsername(username);
            isProfileCompleted = userProfile != null;
        } catch (Exception e) {
            isProfileCompleted = false;
        }

        model.addAttribute("isProfileCompleted", isProfileCompleted);
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
