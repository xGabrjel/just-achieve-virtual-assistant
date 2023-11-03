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
        final BigDecimal proteinFactor = BigDecimal.valueOf(0.2);
        final BigDecimal carbsFactor = BigDecimal.valueOf(0.5);
        final BigDecimal fatFactor = BigDecimal.valueOf(0.3);

        final BigDecimal proteinKcalPerOneGram = BigDecimal.valueOf(4.0);
        final BigDecimal carbsKcalPerOneGram = BigDecimal.valueOf(4.0);
        final BigDecimal fatKcalPerOneGram = BigDecimal.valueOf(9.0);

        BigDecimal bmr = bmrCalculatorService.calculateActivityIncludedBMR(username, activityLevel);

        BigDecimal protein = (bmr.multiply(proteinFactor))
                .divide(proteinKcalPerOneGram,
                        1, RoundingMode.HALF_UP);
        BigDecimal carbs = (bmr.multiply(carbsFactor))
                .divide(carbsKcalPerOneGram,
                        1, RoundingMode.HALF_UP);
        BigDecimal fat = (bmr.multiply(fatFactor))
                .divide(fatKcalPerOneGram,
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

