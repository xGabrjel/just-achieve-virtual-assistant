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

    public String calculateAndInterpretBMI(String username) {
        BigDecimal bmiResult = calculateBMI(username);
        String interpretation;

        if (bmiResult.compareTo(BigDecimal.valueOf(18.5)) < 0) {
            interpretation = "You are below 18.5 – you're in the underweight range";
        } else if (bmiResult.compareTo(BigDecimal.valueOf(24.9)) <= 0) {
            interpretation = "You are between 18.5 and 24.9 – you're in the healthy weight range";
        } else if (bmiResult.compareTo(BigDecimal.valueOf(29.9)) <= 0) {
            interpretation = "You are between 25 and 29.9 – you're in the overweight range";
        } else {
            interpretation = "You are 30 or over – you're in the obese range";
        }

        String message = String.format("BMI value: [%s] - %s", bmiResult, interpretation);
        log.info(message);

        return message;
    }

    public BigDecimal calculateBMI(String username) {
        BigDecimal height = userProfileRepository.findByUserUsername(username).getHeight();
        BigDecimal weight = userProfileRepository.findByUserUsername(username).getWeight();

        log.info("User profile: [%s] - User height is: [%s], Weight is: [%s]".formatted(userProfileRepository
                .findByUserUsername(username), height, weight));

        return weight.divide((height.multiply(height)), RoundingMode.HALF_UP);
    }
}

