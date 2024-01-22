package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.business.FitnessTipsGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import static com.appliaction.justAchieveVirtualAssistant.api.controller.FitnessTipsController.ROOT;

@Controller
@AllArgsConstructor
@RequestMapping(ROOT)
public class FitnessTipsController {

    static final String ROOT = "/tips";

    private FitnessTipsGeneratorService fitnessTipsGeneratorService;

    @GetMapping
    public String tips(
            Model model,
            Principal principal
    ) {
        model.addAttribute("dietGoal", fitnessTipsGeneratorService.getRandomTipForDietGoal(principal.getName())
                .getDietGoal().getDietGoal());
        model.addAttribute("tip", fitnessTipsGeneratorService.getRandomTipForDietGoal(principal.getName())
                .getTip());
        return "tips";
    }
}
