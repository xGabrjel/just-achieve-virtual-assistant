package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.business.MacronutrientsCalculatorService;
import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/macronutrients")
@AllArgsConstructor
public class MacronutrientsController {

    private MacronutrientsCalculatorService macronutrientsCalculatorService;

    @GetMapping
    public String macronutrients(Model model, Principal principal, @RequestParam("activityLevel") ActivityLevel activityLevel) {
        String username = principal.getName();
        Map<String, BigDecimal> macro = macronutrientsCalculatorService.calculateHealthyMacronutrientsValues(username, activityLevel);

        model.addAttribute("macro", macro);
        return "macronutrients";
    }
}
