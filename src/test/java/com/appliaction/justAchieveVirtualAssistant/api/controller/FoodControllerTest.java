package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.business.FoodService;
import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import com.appliaction.justAchieveVirtualAssistant.domain.Item;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@WebMvcTest(FoodController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class FoodControllerTest  {

    private MockMvc mockMvc;

    @MockBean
    private FoodService foodService;

    @Test
    void foodHomePageWorksCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/food"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("food"));
    }

    @Test
    void getFoodDetailsWorksCorrectly() throws Exception {
        //given
        String query = "apple";

        List<Food> foods = new ArrayList<>();
        foods.add(Food.builder()
                .name("Apple")
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
                .build());

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

        when(foodService.findByQuery(query)).thenReturn(Optional.of(new Item(foods)));

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/food/details")
                        .param("query", query))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("food"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("foodDTO"))
                .andExpect(MockMvcResultMatchers.model().attribute("foodDTO", foodDTO));


        verify(foodService, times(1)).findByQuery(query);
    }

    @Test
    void getFoodDetailsWorksCorrectlyWhenFoodNotFound() throws Exception {
        //given
        String query = "nonexistent-food";

        when(foodService.findByQuery(query)).thenReturn(Optional.empty());

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/food/details")
                        .param("query", query))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.handler().handlerType(FoodController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("getFoodDetails"))
                .andExpect(MockMvcResultMatchers.view().name("default-error"));

        verify(foodService, times(1)).findByQuery(query);
    }
}