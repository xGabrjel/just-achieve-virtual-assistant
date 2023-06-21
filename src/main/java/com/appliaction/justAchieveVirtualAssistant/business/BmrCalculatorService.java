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

    public BigDecimal calculateActivityIncludedBMR(String phoneNumber, ActivityLevel activityLevel) {
        BigDecimal bmr = calculateActivityExcludedBMR(phoneNumber);
        BigDecimal armMultiplier = activityLevel.getArmMultiplier();

        BigDecimal result = bmr.multiply(armMultiplier).setScale(2, RoundingMode.HALF_UP);

        log.info("BMR including activity level is equal to: [%s] kcal".formatted(result));
        return result;
    }

    public BigDecimal calculateActivityExcludedBMR(String phoneNumber) {
        BigDecimal weight = userProfileRepository.getUserProfile(phoneNumber).getWeight();
        BigDecimal height = userProfileRepository.getUserProfile(phoneNumber).getHeight();
        Integer age = userProfileRepository.getUserProfile(phoneNumber).getAge();
        String sex = userProfileRepository.getUserProfile(phoneNumber).getSex();

        if (sex.equalsIgnoreCase("MALE")) {
            return calculateByRevisedHarrisBenedictFormulaForMale(weight, height, age);
        } else {
            return calculateByRevisedHarrisBenedictFormulaForFemale(weight, height, age);
        }
    }

    public BigDecimal calculateByRevisedHarrisBenedictFormulaForFemale(BigDecimal weight, BigDecimal height, Integer age) {
        return (((BigDecimal.valueOf(447.6)
                .add(BigDecimal.valueOf(9.25).multiply(weight)))
                .add(BigDecimal.valueOf(310).multiply(height)))
                .subtract(BigDecimal.valueOf(4.33).multiply(BigDecimal.valueOf(age))))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateByRevisedHarrisBenedictFormulaForMale(BigDecimal weight, BigDecimal height, Integer age) {
        return (((BigDecimal.valueOf(88.4)
                .add(BigDecimal.valueOf(13.4).multiply(weight)))
                .add(BigDecimal.valueOf(480).multiply(height)))
                .subtract(BigDecimal.valueOf(5.68).multiply(BigDecimal.valueOf(age))))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
