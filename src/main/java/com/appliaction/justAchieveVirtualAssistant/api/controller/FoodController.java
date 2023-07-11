package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.business.FoodService;
import com.appliaction.justAchieveVirtualAssistant.domain.Item;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/food")
@AllArgsConstructor
public class FoodController {

    private FoodService foodService;

    @GetMapping
    public String foodHomePage() {
        return "food";
    }
    @GetMapping("details")
    public String getFoodDetails(
            Model model,
            @RequestParam String query
            ) {

        Item result = foodService.findByQuery(query)
                .orElseThrow(() -> new NotFoundException("Food: [%s] not found".formatted(query)));

        if (result.getFoods().isEmpty()) {
            return "redirect:/food?error";
        }

        FoodDTO foodDTO = FoodDTO.builder()
                .name(result.getFoods().get(0).getName().toUpperCase())
                .calories(result.getFoods().get(0).getCalories())
                .servingSizeG(result.getFoods().get(0).getServingSizeG())
                .fatTotalG(result.getFoods().get(0).getFatTotalG())
                .fatSaturatedG(result.getFoods().get(0).getFatSaturatedG())
                .proteinG(result.getFoods().get(0).getProteinG())
                .sodiumMg(result.getFoods().get(0).getSodiumMg())
                .potassiumMg(result.getFoods().get(0).getPotassiumMg())
                .cholesterolMg(result.getFoods().get(0).getCholesterolMg())
                .carbohydratesTotalG(result.getFoods().get(0).getCarbohydratesTotalG())
                .fiberG(result.getFoods().get(0).getFiberG())
                .sugarG(result.getFoods().get(0).getSugarG())
                .build();

        model.addAttribute("foodDTO", foodDTO);
        return "food";
    }
}
