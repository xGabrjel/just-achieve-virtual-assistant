package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
public class BmrCalculatorService {

    private final BmiCalculatorService bmiCalculatorService;

    public String calculateProperBMR(String phoneNumber, ActivityLevel activityLevel) {
        switch (activityLevel) {
            case SEDENTARY -> {
                BigDecimal result1 = calculateRawBMR(phoneNumber).multiply(ActivityLevel.SEDENTARY.getArmMultiplier());
                log.info("BMR including activity level is equal to: [%s]".formatted(result1));
                return "Your BMR including activity level is equal to: [%s]".formatted(result1);

            }
            case LIGHTLY_ACTIVE -> {
                BigDecimal result2 = calculateRawBMR(phoneNumber).multiply(ActivityLevel.LIGHTLY_ACTIVE.getArmMultiplier());
                log.info("BMR including activity level is equal to: [%s]".formatted(result2));
                return "Your BMR including activity level is equal to: [%s]".formatted(result2);
            }
            case MODERATELY_ACTIVE -> {
                BigDecimal result3 = calculateRawBMR(phoneNumber).multiply(ActivityLevel.MODERATELY_ACTIVE.getArmMultiplier());
                log.info("BMR including activity level is equal to: [%s]".formatted(result3));
                return "Your BMR including activity level is equal to: [%s]".formatted(result3);
            }
            case ACTIVE -> {
                BigDecimal result4 = calculateRawBMR(phoneNumber).multiply(ActivityLevel.ACTIVE.getArmMultiplier());
                log.info("BMR including activity level is equal to: [%s]".formatted(result4));
                return "Your BMR including activity level is equal to: [%s]".formatted(result4);
            }
            case VERY_ACTIVE -> {
                BigDecimal result5 = calculateRawBMR(phoneNumber).multiply(ActivityLevel.VERY_ACTIVE.getArmMultiplier());
                log.info("BMR including activity level is equal to: [%s]".formatted(result5));
                return "Your BMR including activity level is equal to: [%s]".formatted(result5);
            }
            default -> {
                BigDecimal defaultResult = calculateRawBMR(phoneNumber);
                log.error("Application did not take into account the level of activity");
                return "Application did not take into account the level of activity, default BMR excluding activity: [%s]".formatted(defaultResult);
            }
        }
    }
    private BigDecimal calculateRawBMR(String phoneNumber) {
        BigDecimal weight = bmiCalculatorService.getUserProfile(phoneNumber).getWeight();
        BigDecimal height = bmiCalculatorService.getUserProfile(phoneNumber).getHeight();
        Integer age = bmiCalculatorService.getUserProfile(phoneNumber).getAge();

        return calculateByRevisedHarrisBenedictFormula(weight, height, age);
    }
    private BigDecimal calculateByRevisedHarrisBenedictFormula(BigDecimal weight, BigDecimal height, Integer age) {
        return (BigDecimal.valueOf(88.4).add(BigDecimal.valueOf(13.4).multiply(weight)))
                .add(BigDecimal.valueOf(480).multiply(height))
                .subtract(BigDecimal.valueOf(4.33).multiply(BigDecimal.valueOf(age)));
    }
}
