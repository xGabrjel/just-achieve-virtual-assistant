package com.appliaction.justAchieveVirtualAssistant.api.controller.rest;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.business.FoodService;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Get the nutritional value of your meal!")
    @GetMapping("/get/{foodQuantityAndName}")
    public ResponseEntity<FoodDTO> getFood(
            @Parameter(description = "Quantity of products and its name - example: 150g of Lays")
            @PathVariable String foodQuantityAndName
    ) {

        var result = foodService.findByQuery(foodQuantityAndName)
                .orElseThrow(() -> new NotFoundException("Food: [%s] not found".formatted(foodQuantityAndName)));

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
