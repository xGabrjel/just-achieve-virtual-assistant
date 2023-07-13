package com.appliaction.justAchieveVirtualAssistant.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "calories",
        "serving_size_g",
        "fat_total_g",
        "fat_saturated_g",
        "protein_g",
        "sodium_mg",
        "potassium_mg",
        "cholesterol_mg",
        "carbohydrates_total_g",
        "fiber_g",
        "sugar_g"
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "foodId")
@ToString(of = {"foodId", "name", "servingSizeG"})
public class Food {

    @JsonIgnore
    private Integer foodId;
    @JsonIgnore
    private User userId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("calories")
    private BigDecimal calories;
    @JsonProperty("serving_size_g")
    private Integer servingSizeG;
    @JsonProperty("fat_total_g")
    private BigDecimal fatTotalG;
    @JsonProperty("fat_saturated_g")
    private BigDecimal fatSaturatedG;
    @JsonProperty("protein_g")
    private BigDecimal proteinG;
    @JsonProperty("sodium_mg")
    private BigDecimal sodiumMg;
    @JsonProperty("potassium_mg")
    private BigDecimal potassiumMg;
    @JsonProperty("cholesterol_mg")
    private BigDecimal cholesterolMg;
    @JsonProperty("carbohydrates_total_g")
    private BigDecimal carbohydratesTotalG;
    @JsonProperty("fiber_g")
    private BigDecimal fiberG;
    @JsonProperty("sugar_g")
    private BigDecimal sugarG;
}
