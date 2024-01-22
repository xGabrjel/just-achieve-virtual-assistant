package com.appliaction.justAchieveVirtualAssistant.api.dto.mapper;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FoodMapperImplTest {

    private FoodMapper foodMapper;

    @BeforeEach
    void setUp() {
        foodMapper = new FoodMapperImpl();
    }

    @Test
    void shouldMapFoodToFoodDTO() {
        //given
        Food food = new Food();
        food.setName("Example Food");
        food.setCalories(BigDecimal.valueOf(200));
        food.setCalories(BigDecimal.valueOf(200));
        food.setServingSizeG(200);
        food.setFatTotalG(BigDecimal.valueOf(200));
        food.setFatSaturatedG(BigDecimal.valueOf(200));
        food.setProteinG(BigDecimal.valueOf(200));
        food.setSodiumMg(BigDecimal.valueOf(200));
        food.setPotassiumMg(BigDecimal.valueOf(200));
        food.setCholesterolMg(BigDecimal.valueOf(200));
        food.setCarbohydratesTotalG(BigDecimal.valueOf(200));
        food.setFiberG(BigDecimal.valueOf(200));
        food.setSugarG(BigDecimal.valueOf(200));

        //when
        FoodDTO foodDTO = foodMapper.map(food);

        //then
        assertEquals(food.getName(), foodDTO.getName());
        assertEquals(food.getCalories(), foodDTO.getCalories());
        assertEquals(food.getServingSizeG(), foodDTO.getServingSizeG());
        assertEquals(food.getFatTotalG(), foodDTO.getFatTotalG());
        assertEquals(food.getFatSaturatedG(), foodDTO.getFatSaturatedG());
        assertEquals(food.getProteinG(), foodDTO.getProteinG());
        assertEquals(food.getSodiumMg(), foodDTO.getSodiumMg());
        assertEquals(food.getPotassiumMg(), foodDTO.getPotassiumMg());
        assertEquals(food.getCholesterolMg(), foodDTO.getCholesterolMg());
        assertEquals(food.getCarbohydratesTotalG(), foodDTO.getCarbohydratesTotalG());
        assertEquals(food.getFiberG(), foodDTO.getFiberG());
        assertEquals(food.getSugarG(), foodDTO.getSugarG());
    }

    @Test
    void shouldMapNullFoodToNullFoodDTO() {
        //given
        FoodDTO foodDTO = foodMapper.map(null);

        //when, then
        assertNull(foodDTO);
    }

    @Test
    void shouldMapFoodWithNullPropertiesToFoodDTO() {
        //given
        Food food = new Food(); // All properties are null

        //when
        FoodDTO foodDTO = foodMapper.map(food);

        //then
        assertNull(foodDTO.getName());
        assertNull(foodDTO.getCalories());
        assertNull(foodDTO.getServingSizeG());
        assertNull(foodDTO.getFatTotalG());
        assertNull(foodDTO.getFatSaturatedG());
        assertNull(foodDTO.getProteinG());
        assertNull(foodDTO.getSodiumMg());
        assertNull(foodDTO.getPotassiumMg());
        assertNull(foodDTO.getCholesterolMg());
        assertNull(foodDTO.getCarbohydratesTotalG());
        assertNull(foodDTO.getFiberG());
        assertNull(foodDTO.getSugarG());
    }
}