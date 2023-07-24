package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.business.FoodService;
import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import com.appliaction.justAchieveVirtualAssistant.domain.Item;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/food")
public class FoodController {

    private FoodService foodService;

    @GetMapping
    public String foodHomePage() {
        return "food";
    }

    @GetMapping("/details")
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

    @PostMapping("/new-details")
    public String saveProduct(
            @ModelAttribute FoodDTO foodDTO,
            Principal principal
    ) {
        String username = principal.getName();
        foodService.saveProduct(foodDTO, username);
        return "redirect:/food?success";
    }

    @GetMapping("/all-products")
    public String loadAllProducts(
            Model model,
            Principal principal
    ) {
        String username = principal.getName();
        List<Food> listOFFoods = foodService.findAllByUsername(username);
        List<FoodDTO> allProducts = new ArrayList<>();

        for (Food foods : listOFFoods) {
            FoodDTO foodDTO = FoodDTO.builder()
                    .name(foods.getName())
                    .calories(foods.getCalories())
                    .servingSizeG(foods.getServingSizeG())
                    .fatTotalG(foods.getFatTotalG())
                    .fatSaturatedG(foods.getFatSaturatedG())
                    .proteinG(foods.getProteinG())
                    .sodiumMg(foods.getSodiumMg())
                    .potassiumMg(foods.getPotassiumMg())
                    .cholesterolMg(foods.getCholesterolMg())
                    .carbohydratesTotalG(foods.getCarbohydratesTotalG())
                    .fiberG(foods.getFiberG())
                    .sugarG(foods.getSugarG())
                    .build();

            allProducts.add(foodDTO);
        }

        Integer totalServingSize = allProducts.stream()
                .map(FoodDTO::getServingSizeG)
                .reduce(0, Integer::sum);
        BigDecimal totalCalories = allProducts.stream()
                .map(FoodDTO::getCalories)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalFatTotal = allProducts.stream()
                .map(FoodDTO::getFatTotalG)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalFatSaturated = allProducts.stream()
                .map(FoodDTO::getFatSaturatedG)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalProtein = allProducts.stream()
                .map(FoodDTO::getProteinG)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalSodium = allProducts.stream()
                .map(FoodDTO::getSodiumMg)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalPotassium = allProducts.stream()
                .map(FoodDTO::getPotassiumMg)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCholesterol = allProducts.stream()
                .map(FoodDTO::getCholesterolMg)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCarbohydrates = allProducts.stream()
                .map(FoodDTO::getCarbohydratesTotalG)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalFiber = allProducts.stream()
                .map(FoodDTO::getFiberG)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalSugar = allProducts.stream()
                .map(FoodDTO::getSugarG)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("allProducts", allProducts);
        model.addAttribute("totalServingSize", totalServingSize);
        model.addAttribute("totalCalories", totalCalories);
        model.addAttribute("totalFatTotal", totalFatTotal);
        model.addAttribute("totalFatSaturated", totalFatSaturated);
        model.addAttribute("totalProtein", totalProtein);
        model.addAttribute("totalSodium", totalSodium);
        model.addAttribute("totalPotassium", totalPotassium);
        model.addAttribute("totalCholesterol", totalCholesterol);
        model.addAttribute("totalCarbohydrates", totalCarbohydrates);
        model.addAttribute("totalFiber", totalFiber);
        model.addAttribute("totalSugar", totalSugar);
        return "food";
    }

    @GetMapping("/remover")
    public String deleteAllProducts() {
        foodService.deleteAll();
        return "redirect:/food?success";
    }
}
