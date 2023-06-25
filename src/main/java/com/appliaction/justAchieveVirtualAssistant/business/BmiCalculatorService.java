package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@AllArgsConstructor
public class BmiCalculatorService {

    private final UserProfileRepository userProfileRepository;

    public String interpretBMI(String username) {
        BigDecimal bmiResult = calculateBMI(username);

        int underweight = bmiResult.compareTo(BigDecimal.valueOf(18.5));
        int healthy = bmiResult.compareTo(BigDecimal.valueOf(24.9));
        int overweight = bmiResult.compareTo(BigDecimal.valueOf(29.9));

        if (underweight < 0) {
            log.info("BMI value: [%s] - You are below 18.5 – you're in the underweight range".formatted(bmiResult));
            return "BMI value: [%s] - You are below 18.5 – you're in the underweight range".formatted(bmiResult);
        }
        if (underweight > 0 && healthy < 0) {
            log.info("BMI value: [%s] - You are between 18.5 and 24.9 – you're in the healthy weight range".formatted(bmiResult));
            return "BMI value: [%s] - You are between 18.5 and 24.9 – you're in the healthy weight range".formatted(bmiResult);
        }
        if (healthy > 0 && overweight < 0) {
            log.info("BMI value: [%s] - You are between 25 and 29.9 – you're in the overweight range".formatted(bmiResult));
            return "BMI value: [%s] - You are between 25 and 29.9 – you're in the overweight range".formatted(bmiResult);
        }
        log.info("BMI value: [%s] - You are 30 or over – you're in the obese range".formatted(bmiResult));
        return "BMI value: [%s] - You are 30 or over – you're in the obese range".formatted(bmiResult);
    }

    public BigDecimal calculateBMI(String username) {
        BigDecimal height = userProfileRepository.findByUserUsername(username).getHeight();
        BigDecimal weight = userProfileRepository.findByUserUsername(username).getWeight();

        log.info("User profile: [%s] - User height is: [%s], Weight is: [%s]".formatted(userProfileRepository
                .findByUserUsername(username), height, weight));

        return weight.divide((height.multiply(height)), RoundingMode.HALF_UP);
    }
}

