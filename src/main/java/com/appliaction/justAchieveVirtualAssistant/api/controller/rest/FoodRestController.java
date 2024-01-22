package com.appliaction.justAchieveVirtualAssistant.api.controller.rest;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.business.FoodService;
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

import static com.appliaction.justAchieveVirtualAssistant.api.controller.rest.FoodRestController.ROOT;

@RestController
@AllArgsConstructor
@RequestMapping(ROOT)
public class FoodRestController {

    static final String ROOT = "/food-manager";
    static final String GET = "/nutrients/{foodQuantityAndName}";

    static final String GET_MESSAGE = "Get the nutritional value of your meal!";

    private FoodService foodService;

    @Operation(summary = GET_MESSAGE)
    @GetMapping(GET)
    public ResponseEntity<FoodDTO> getFood(
            @Parameter(description = "Quantity of products and its name - example: 150g of Lays")
            @PathVariable String foodQuantityAndName
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(foodService.findFinalProduct(foodQuantityAndName));
    }
}
