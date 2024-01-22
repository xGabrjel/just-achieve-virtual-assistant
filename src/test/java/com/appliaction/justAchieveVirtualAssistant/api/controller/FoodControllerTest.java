package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.business.FoodService;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FoodController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class FoodControllerTest  {

    private MockMvc mockMvc;
    @MockBean
    private FoodService foodService;

    @Test
    void foodHomePageWorksCorrectly() throws Exception {
        //given, when, then
        mockMvc.perform(get("/food"))
                .andExpect(status().isOk())
                .andExpect(view().name("food"));
    }

    @Test
    @WithMockUser
    void saveProductWorksCorrectly() throws Exception {
        //given
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setName("Apple");

        String username = "admin";
        Principal principal = () -> username;

        //when, then
        mockMvc.perform(post("/food/new-details")
                .flashAttr("foodDTO", foodDTO)
                .principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/food?success"));

        verify(foodService, times(1)).saveProduct(foodDTO, "admin");
    }

    @Test
    void getFoodDetailsWorksCorrectly() throws Exception {
        //given
        String query = "apple";
        FoodDTO foodDTO = FoodDTO.builder()
                .name("APPLE")
                .calories(new BigDecimal("52"))
                .servingSizeG(100)
                .fatTotalG(new BigDecimal("0.2"))
                .fatSaturatedG(new BigDecimal("0"))
                .proteinG(new BigDecimal("0.3"))
                .sodiumMg(new BigDecimal("1"))
                .potassiumMg(new BigDecimal("107"))
                .cholesterolMg(new BigDecimal("0"))
                .carbohydratesTotalG(new BigDecimal("14"))
                .fiberG(new BigDecimal("2.4"))
                .sugarG(new BigDecimal("10.3"))
                .build();

        when(foodService.findFinalProduct(query))
                .thenReturn(foodDTO);

        //when, then
        mockMvc.perform(get("/food/details")
                        .param("query", query))
                .andExpect(status().isOk())
                .andExpect(view().name("food"))
                .andExpect(model().attributeExists("foodDTO"))
                .andExpect(model().attribute("foodDTO", foodDTO));


        verify(foodService, times(1)).findFinalProduct(query);
    }

    @Test
    void getFoodDetailsWhenFoodNotFoundWorksCorrectly() throws Exception {
        //given
        String query = "nonexistent-food";

        when(foodService.findFinalProduct(query)).thenThrow(NotFoundException.class);

        //when, then
        mockMvc.perform(get("/food/details")
                        .param("query", query))
                .andExpect(status().isNotFound())
                .andExpect(handler().handlerType(FoodController.class))
                .andExpect(handler().methodName("getFoodDetails"))
                .andExpect(view().name("default-error"));

        verify(foodService, times(1)).findFinalProduct(query);
    }

    @Test
    @WithMockUser
    public void loadAllProductsWorksCorrectly() throws Exception {
        //given
        String username = "admin";
        Principal principal = () -> username;

        List<FoodDTO> listOfFoods = new ArrayList<>();

        FoodDTO food1 = FoodDTO.builder()
                .name("Food 1")
                .calories(BigDecimal.valueOf(100))
                .servingSizeG(50)
                .fatTotalG(BigDecimal.valueOf(5))
                .fatSaturatedG(BigDecimal.valueOf(2))
                .proteinG(BigDecimal.valueOf(10))
                .sodiumMg(BigDecimal.valueOf(100))
                .potassiumMg(BigDecimal.valueOf(200))
                .cholesterolMg(BigDecimal.valueOf(20))
                .carbohydratesTotalG(BigDecimal.valueOf(30))
                .fiberG(BigDecimal.valueOf(5))
                .sugarG(BigDecimal.valueOf(10))
                .build();

        FoodDTO food2 = FoodDTO.builder()
                .name("Food 2")
                .calories(BigDecimal.valueOf(200))
                .servingSizeG(100)
                .fatTotalG(BigDecimal.valueOf(8))
                .fatSaturatedG(BigDecimal.valueOf(4))
                .proteinG(BigDecimal.valueOf(20))
                .sodiumMg(BigDecimal.valueOf(150))
                .potassiumMg(BigDecimal.valueOf(300))
                .cholesterolMg(BigDecimal.valueOf(30))
                .carbohydratesTotalG(BigDecimal.valueOf(40))
                .fiberG(BigDecimal.valueOf(8))
                .sugarG(BigDecimal.valueOf(15))
                .build();

        listOfFoods.add(food1);
        listOfFoods.add(food2);

        when(foodService.findAllDTOByUsername(username))
                .thenReturn(listOfFoods);

        //when, then
        mockMvc.perform(get("/food/all-products")
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("food"))
                .andExpect(model().attributeExists("allProducts"))
                .andExpect(model().attribute("allProducts", hasSize(2)))
                .andExpect(model().attributeExists("totalServingSize"))
                .andExpect(model().attribute("totalServingSize", 150))
                .andExpect(model().attributeExists("totalCalories"))
                .andExpect(model().attribute("totalCalories", BigDecimal.valueOf(300)))
                .andExpect(model().attributeExists("totalFatTotal"))
                .andExpect(model().attribute("totalFatTotal", BigDecimal.valueOf(13)))
                .andExpect(model().attributeExists("totalFatSaturated"))
                .andExpect(model().attribute("totalFatSaturated", BigDecimal.valueOf(6)))
                .andExpect(model().attributeExists("totalProtein"))
                .andExpect(model().attribute("totalProtein", BigDecimal.valueOf(30)))
                .andExpect(model().attributeExists("totalSodium"))
                .andExpect(model().attribute("totalSodium", BigDecimal.valueOf(250)))
                .andExpect(model().attributeExists("totalPotassium"))
                .andExpect(model().attribute("totalPotassium", BigDecimal.valueOf(500)))
                .andExpect(model().attributeExists("totalCholesterol"))
                .andExpect(model().attribute("totalCholesterol", BigDecimal.valueOf(50)))
                .andExpect(model().attributeExists("totalCarbohydrates"))
                .andExpect(model().attribute("totalCarbohydrates", BigDecimal.valueOf(70)))
                .andExpect(model().attributeExists("totalFiber"))
                .andExpect(model().attribute("totalFiber", BigDecimal.valueOf(13)))
                .andExpect(model().attributeExists("totalSugar"))
                .andExpect(model().attribute("totalSugar", BigDecimal.valueOf(25)));

        verify(foodService, times(1)).findAllDTOByUsername(anyString());
    }

    @Test
    @WithMockUser
    public void deleteAllProductsWorksCorrectly() throws Exception {
        //given, when, then
        mockMvc.perform(get("/food/remover"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/food?success"));

        verify(foodService, times(1)).deleteAll();
    }
}