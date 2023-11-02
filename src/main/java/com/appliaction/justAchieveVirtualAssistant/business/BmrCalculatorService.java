package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@AllArgsConstructor
public class BmrCalculatorService {

    private final UserProfileRepository userProfileRepository;

    public BigDecimal calculateActivityIncludedBMR(String username, ActivityLevel activityLevel) {
        BigDecimal bmr = calculateActivityExcludedBMR(username);
        BigDecimal armMultiplier = activityLevel.getArmMultiplier();

        BigDecimal result = bmr.multiply(armMultiplier).setScale(2, RoundingMode.HALF_UP);

        log.info("BMR including activity level is equal to: [%s] kcal".formatted(result));
        return result;
    }

    public BigDecimal calculateActivityExcludedBMR(String username) {
        BigDecimal weight = userProfileRepository.findByUserUsername(username).getWeight();
        BigDecimal height = userProfileRepository.findByUserUsername(username).getHeight();
        Integer age = userProfileRepository.findByUserUsername(username).getAge();
        String sex = userProfileRepository.findByUserUsername(username).getSex();

        if (sex.equalsIgnoreCase("MALE")) {
            return calculateByRevisedHarrisBenedictFormulaForMale(weight, height, age);
        } else {
            return calculateByRevisedHarrisBenedictFormulaForFemale(weight, height, age);
        }
    }

    public BigDecimal calculateByRevisedHarrisBenedictFormulaForFemale(BigDecimal weight, BigDecimal height, Integer age) {

        final BigDecimal femaleWeightFactor = BigDecimal.valueOf(9.25);
        final BigDecimal femaleHeightFactor = BigDecimal.valueOf(310);
        final BigDecimal femaleAgeFactor = BigDecimal.valueOf(4.33);
        final BigDecimal femaleAdditiveFactor = BigDecimal.valueOf(447.6);

        return (((femaleAdditiveFactor
                .add(femaleWeightFactor.multiply(weight)))
                .add(femaleHeightFactor.multiply(height)))
                .subtract(femaleAgeFactor.multiply(BigDecimal.valueOf(age))))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateByRevisedHarrisBenedictFormulaForMale(BigDecimal weight, BigDecimal height, Integer age) {

        final BigDecimal maleWeightFactor = BigDecimal.valueOf(13.4);
        final BigDecimal maleHeightFactor = BigDecimal.valueOf(480);
        final BigDecimal maleAgeFactor = BigDecimal.valueOf(5.68);
        final BigDecimal maleAdditiveFactor = BigDecimal.valueOf(88.4);

        return (((maleAdditiveFactor
                .add(maleWeightFactor.multiply(weight)))
                .add(maleHeightFactor.multiply(height)))
                .subtract(maleAgeFactor.multiply(BigDecimal.valueOf(age))))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
