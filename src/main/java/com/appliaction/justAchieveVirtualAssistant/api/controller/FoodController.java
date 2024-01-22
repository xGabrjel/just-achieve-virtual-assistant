package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.business.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import static com.appliaction.justAchieveVirtualAssistant.api.controller.FoodController.ROOT;

@Controller
@AllArgsConstructor
@RequestMapping(ROOT)
public class FoodController {

    static final String ROOT = "/food";
    static final String DETAILS = "/details";
    static final String SAVE_PRODUCT = "/new-details";
    static final String ALL_PRODUCTS = "/all-products";
    static final String DELETE_ALL = "/remover";

    private FoodService foodService;

    @GetMapping
    public String foodHomePage() {
        return "food";
    }

    @GetMapping(DETAILS)
    public String getFoodDetails(
            Model model,
            @RequestParam String query
    ) {
        model.addAttribute("foodDTO", foodService.findFinalProduct(query));
        return "food";
    }

    @PostMapping(SAVE_PRODUCT)
    public String saveProduct(
            @ModelAttribute FoodDTO foodDTO,
            Principal principal
    ) {
        foodService.saveProduct(foodDTO, principal.getName());
        return "redirect:/food?success";
    }

    @GetMapping(DELETE_ALL)
    public String deleteAllProducts() {
        foodService.deleteAll();
        return "redirect:/food?success";
    }

    @GetMapping(ALL_PRODUCTS)
    public String loadAllProducts(
            Model model,
            Principal principal
    ) {
        List<FoodDTO> resultList = foodService.findAllDTOByUsername(principal.getName());

        model.addAttribute("allProducts", resultList);
        model.addAttribute("totalSugar", resultList.stream().map(FoodDTO::getSugarG).reduce(BigDecimal.ZERO, BigDecimal::add));
        model.addAttribute("totalFiber", resultList.stream().map(FoodDTO::getFiberG).reduce(BigDecimal.ZERO, BigDecimal::add));
        model.addAttribute("totalServingSize", resultList.stream().map(FoodDTO::getServingSizeG).reduce(0, Integer::sum));
        model.addAttribute("totalSodium", resultList.stream().map(FoodDTO::getSodiumMg).reduce(BigDecimal.ZERO, BigDecimal::add));
        model.addAttribute("totalProtein", resultList.stream().map(FoodDTO::getProteinG).reduce(BigDecimal.ZERO, BigDecimal::add));
        model.addAttribute("totalCalories", resultList.stream().map(FoodDTO::getCalories).reduce(BigDecimal.ZERO, BigDecimal::add));
        model.addAttribute("totalFatTotal", resultList.stream().map(FoodDTO::getFatTotalG).reduce(BigDecimal.ZERO, BigDecimal::add));
        model.addAttribute("totalPotassium", resultList.stream().map(FoodDTO::getPotassiumMg).reduce(BigDecimal.ZERO, BigDecimal::add));
        model.addAttribute("totalCholesterol", resultList.stream().map(FoodDTO::getCholesterolMg).reduce(BigDecimal.ZERO, BigDecimal::add));
        model.addAttribute("totalFatSaturated", resultList.stream().map(FoodDTO::getFatSaturatedG).reduce(BigDecimal.ZERO, BigDecimal::add));
        model.addAttribute("totalCarbohydrates", resultList.stream().map(FoodDTO::getCarbohydratesTotalG).reduce(BigDecimal.ZERO, BigDecimal::add));
        return "food";
    }
}
