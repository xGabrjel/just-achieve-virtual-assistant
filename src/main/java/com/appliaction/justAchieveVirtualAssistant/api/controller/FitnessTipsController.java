package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FitnessTipsDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.FitnessTipsMapper;
import com.appliaction.justAchieveVirtualAssistant.business.FitnessTipsGeneratorService;
import com.appliaction.justAchieveVirtualAssistant.domain.FitnessTips;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/tips")
@AllArgsConstructor
public class FitnessTipsController {

    private FitnessTipsGeneratorService fitnessTipsGeneratorService;
    private FitnessTipsMapper fitnessTipsMapper;

    @GetMapping
    public String tips(
            Model model,
            Principal principal
    ) {
        String username = principal.getName();
        FitnessTips randomTipForDietGoal = fitnessTipsGeneratorService.getRandomTipForDietGoal(username);
        FitnessTipsDTO tipsDTO = fitnessTipsMapper.map(randomTipForDietGoal);

        model.addAttribute("dietGoal", tipsDTO.getDietGoal().getDietGoal());
        model.addAttribute("tip", tipsDTO.getTip());
        return "tips";
    }
}
