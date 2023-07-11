package com.appliaction.justAchieveVirtualAssistant.api.controller.rest;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.business.FoodService;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/food-manager")
public class FoodRestController {

    private FoodService foodService;

    @GetMapping("/get/{foodName}")
    public ResponseEntity<FoodDTO> getFood(@PathVariable String foodName) {

        var result = foodService.findByQuery(foodName)
                .orElseThrow(() -> new NotFoundException("Food: [%s] not found".formatted(foodName)));

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

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(foodDTO);
    }
}
