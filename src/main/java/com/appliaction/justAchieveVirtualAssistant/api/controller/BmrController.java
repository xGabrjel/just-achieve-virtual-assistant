package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.business.BmrCalculatorService;
import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/bmr")
@AllArgsConstructor
public class BmrController {

    private BmrCalculatorService bmrCalculatorService;

    @GetMapping
    public String page() {
        return "bmr";
    }

    @GetMapping("/calculate/{activityLevel}")
    public String bmr(
            Model model,
            Principal principal,
            @RequestParam("activityLevel") ActivityLevel activityLevel
    ) {
        String username = principal.getName();
        BigDecimal bmr = bmrCalculatorService.calculateActivityIncludedBMR(username, activityLevel);

        model.addAttribute("bmr", bmr);
        return "bmr";
    }
}
