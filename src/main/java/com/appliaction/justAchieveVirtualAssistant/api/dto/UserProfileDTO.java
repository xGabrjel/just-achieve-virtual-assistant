package com.appliaction.justAchieveVirtualAssistant.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {

    private String name;
    private String surname;
    @Length(min = 7, max = 15)
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String phone;
    @Min(1)
    @Max(120)
    private Integer age;
    private String sex;
    private BigDecimal weight;
    @Min(1)
    @Max(3)
    private BigDecimal height;
    private DietGoalsDTO dietGoal;
}
