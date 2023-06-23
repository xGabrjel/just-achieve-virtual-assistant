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
public class UserProfileDTO {

    private String name;
    private String surname;
    private String phone;
    private Integer age;
    private String sex;
    private BigDecimal weight;
    private BigDecimal height;
    private DietGoalsDTO dietGoal;
}
