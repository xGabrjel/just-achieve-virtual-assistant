package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.business.BmiCalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import static com.appliaction.justAchieveVirtualAssistant.api.controller.BmiController.ROOT;

@Controller
@AllArgsConstructor
@RequestMapping(ROOT)
public class BmiController {

    static final String ROOT = "/bmi";

    private BmiCalculatorService bmiCalculatorService;

    @GetMapping
    public String bmi(
            Model model,
            Principal principal
    ) {
        model.addAttribute("bmi", bmiCalculatorService.calculateAndInterpretBMI(principal.getName()));
        return "bmi";
    }
}
