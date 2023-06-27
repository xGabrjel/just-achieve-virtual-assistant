package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.business.BmiCalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/bmi")
@AllArgsConstructor
public class BmiController {

    private BmiCalculatorService bmiCalculatorService;

    @GetMapping
    public String bmi(
            Model model,
            Principal principal
    ) {
        String username = principal.getName();
        String bmi = bmiCalculatorService.calculateAndInterpretBMI(username);

        model.addAttribute("bmi", bmi);
        return "bmi";
    }
}
