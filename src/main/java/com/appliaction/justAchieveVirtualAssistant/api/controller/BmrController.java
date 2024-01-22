package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.business.BmrCalculatorService;
import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import static com.appliaction.justAchieveVirtualAssistant.api.controller.BmrController.ROOT;

@Controller
@AllArgsConstructor
@RequestMapping(ROOT)
public class BmrController {

    static final String ROOT = "/bmr";
    static final String BMR = "/{activityLevel}";

    private BmrCalculatorService bmrCalculatorService;

    @GetMapping
    public String page() {
        return "bmr";
    }

    @GetMapping(BMR)
    public String bmr(
            Model model,
            Principal principal,
            @RequestParam("activityLevel") ActivityLevel activityLevel
    ) {
        model.addAttribute("bmr", bmrCalculatorService.calculateActivityIncludedBMR(principal.getName(), activityLevel));
        return "bmr";
    }
}
