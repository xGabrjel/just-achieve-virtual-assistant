package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class MacronutrientsCalculatorService {

    private BmrCalculatorService bmrCalculatorService;

    public Map<String, BigDecimal> calculateHealthyMacronutrientsValues(String phoneNumber, ActivityLevel activityLevel) {
        BigDecimal bmr = bmrCalculatorService.calculateActivityIncludedBMR(phoneNumber, activityLevel);

        BigDecimal protein = (bmr.multiply(BigDecimal.valueOf(0.2)))
                .divide(BigDecimal.valueOf(4.0),
                        RoundingMode.HALF_UP);
        BigDecimal carbs = (bmr.multiply(BigDecimal.valueOf(0.5)))
                .divide(BigDecimal.valueOf(4.0),
                        RoundingMode.HALF_UP);
        BigDecimal fat = (bmr.multiply(BigDecimal.valueOf(0.3)))
                .divide(BigDecimal.valueOf(9.0),
                        RoundingMode.HALF_UP);

        log.info("Default macronutrients values for BMR [%s] kcal: Protein - [%s], Carbohydrates - [%s], Fat - [%s]"
                .formatted(bmr, protein, carbs, fat));

        return Map.of("BMR", bmr, "Protein", protein, "Carbohydrates", carbs, "Fats", fat);
    }
}

