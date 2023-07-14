package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class MacronutrientsCalculatorService {

    private BmrCalculatorService bmrCalculatorService;

    public Map<String, BigDecimal> calculateHealthyMacronutrientsValues(String username, ActivityLevel activityLevel) {
        BigDecimal bmr = bmrCalculatorService.calculateActivityIncludedBMR(username, activityLevel);

        BigDecimal protein = (bmr.multiply(BigDecimal.valueOf(0.2)))
                .divide(BigDecimal.valueOf(4.0),
                        1 ,RoundingMode.HALF_UP);
        BigDecimal carbs = (bmr.multiply(BigDecimal.valueOf(0.5)))
                .divide(BigDecimal.valueOf(4.0),
                        1 ,RoundingMode.HALF_UP);
        BigDecimal fat = (bmr.multiply(BigDecimal.valueOf(0.3)))
                .divide(BigDecimal.valueOf(9.0),
                        1, RoundingMode.HALF_UP);

        log.info("Default macronutrients values for BMR [%s] kcal: Protein - [%s], Carbohydrates - [%s], Fat - [%s]"
                .formatted(bmr, protein, carbs, fat));

        Map<String, BigDecimal> result = new HashMap<>();
        result.put("bmr", bmr);
        result.put("protein", protein);
        result.put("carbohydrates", carbs);
        result.put("fats", fat);
        return result;
    }
}

