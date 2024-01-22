package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.business.MacronutrientsCalculatorService;
import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import static com.appliaction.justAchieveVirtualAssistant.api.controller.MacronutrientsController.ROOT;

@Controller
@AllArgsConstructor
@RequestMapping(ROOT)
public class MacronutrientsController {

    static final String ROOT = "/macronutrients";
    static final String MACRONUTRIENTS = "/{activityLevel}";

    private MacronutrientsCalculatorService macronutrientsCalculatorService;

    @GetMapping
    public String page() {
        return "macronutrients";
    }

    @GetMapping(MACRONUTRIENTS)
    public String macronutrients(
            Model model,
            Principal principal,
            @RequestParam("activityLevel") ActivityLevel activityLevel
    ) {
        model.addAttribute("macro", macronutrientsCalculatorService
                .calculateHealthyMacronutrientsValues(principal.getName(), activityLevel));
        return "macronutrients";
    }
}
