package com.appliaction.justAchieveVirtualAssistant.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {

    private String name;
    private BigDecimal calories;
    private Integer servingSizeG;
    private BigDecimal fatTotalG;
    private BigDecimal fatSaturatedG;
    private BigDecimal proteinG;
    private BigDecimal sodiumMg;
    private BigDecimal potassiumMg;
    private BigDecimal cholesterolMg;
    private BigDecimal carbohydratesTotalG;
    private BigDecimal fiberG;
    private BigDecimal sugarG;
}
