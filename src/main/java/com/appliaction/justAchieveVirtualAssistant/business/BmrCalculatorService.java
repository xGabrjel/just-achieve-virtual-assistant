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

    public String calculateActivityIncludedBMR(String phoneNumber, ActivityLevel activityLevel) {
        switch (activityLevel) {
            case SEDENTARY -> {
                BigDecimal result1 = calculateActivityExcludedBMR(phoneNumber)
                        .multiply(ActivityLevel.SEDENTARY.getArmMultiplier())
                        .setScale(2, RoundingMode.HALF_UP);

                log.info("BMR including activity level is equal to: [%s] kcal".formatted(result1));
                return "Your BMR including activity level is equal to: [%s] kcal".formatted(result1);

            }
            case LIGHTLY_ACTIVE -> {
                BigDecimal result2 = calculateActivityExcludedBMR(phoneNumber)
                        .multiply(ActivityLevel.LIGHTLY_ACTIVE.getArmMultiplier())
                        .setScale(2, RoundingMode.HALF_UP);

                log.info("BMR including activity level is equal to: [%s] kcal".formatted(result2));
                return "Your BMR including activity level is equal to: [%s] kcal".formatted(result2);
            }
            case MODERATELY_ACTIVE -> {
                BigDecimal result3 = calculateActivityExcludedBMR(phoneNumber)
                        .multiply(ActivityLevel.MODERATELY_ACTIVE.getArmMultiplier())
                        .setScale(2, RoundingMode.HALF_UP);

                log.info("BMR including activity level is equal to: [%s] kcal".formatted(result3));
                return "Your BMR including activity level is equal to: [%s] kcal".formatted(result3);
            }
            case ACTIVE -> {
                BigDecimal result4 = calculateActivityExcludedBMR(phoneNumber)
                        .multiply(ActivityLevel.ACTIVE.getArmMultiplier())
                        .setScale(2, RoundingMode.HALF_UP);

                log.info("BMR including activity level is equal to: [%s] kcal".formatted(result4));
                return "Your BMR including activity level is equal to: [%s] kcal".formatted(result4);
            }
            case VERY_ACTIVE -> {
                BigDecimal result5 = calculateActivityExcludedBMR(phoneNumber)
                        .multiply(ActivityLevel.VERY_ACTIVE.getArmMultiplier())
                        .setScale(2, RoundingMode.HALF_UP);

                log.info("BMR including activity level is equal to: [%s] kcal".formatted(result5));
                return "Your BMR including activity level is equal to: [%s] kcal".formatted(result5);
            }
            default -> {
                BigDecimal defaultResult = calculateActivityExcludedBMR(phoneNumber);

                log.error("Application did not take into account the level of activity");
                return "Application did not take into account the level of activity, default BMR excluding activity: [%s] kcal".formatted(defaultResult);
            }
        }
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
