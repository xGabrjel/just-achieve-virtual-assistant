package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.business.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import static com.appliaction.justAchieveVirtualAssistant.api.controller.HomeController.ROOT;

@Controller
@RequestMapping(ROOT)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HomeController {

    static final String ROOT = "/";
    static final String LOGIN = "/login";
    static final String ERROR = "/error";

    private final UserProfileService userProfileService;

    @GetMapping
    public String homePage(
            Model model,
            Principal principal
    ) {
        model.addAttribute("isProfileCompleted", userProfileService.isProfileCompleted(principal));
        return "home";
    }

    @GetMapping(LOGIN)
    public String login() {
        return "login";
    }

    @GetMapping(ERROR)
    public String error() {
        return "error";
    }
}
