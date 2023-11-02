package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@AllArgsConstructor
public class BmrCalculatorService {

    private final UserProfileRepository userProfileRepository;

    public BigDecimal calculateActivityIncludedBMR(String username, ActivityLevel activityLevel) {
        BigDecimal bmr = calculateActivityExcludedBMR(username);
        BigDecimal armMultiplier = activityLevel.getArmMultiplier();

        return bmr.multiply(armMultiplier).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateActivityExcludedBMR(String username) {
        UserProfile userProfile = userProfileRepository.findByUserUsername(username);
        BigDecimal weight = userProfile.getWeight();
        BigDecimal height = userProfile.getHeight();
        Integer age = userProfile.getAge();
        String sex = userProfile.getSex();

        if (isMale(sex)) {
            return calculateByRevisedHarrisBenedictFormulaForMale(weight, height, age);
        } else {
            return calculateByRevisedHarrisBenedictFormulaForFemale(weight, height, age);
        }
    }

    private boolean isMale(String sex) {
        return sex != null && sex.equalsIgnoreCase("MALE");
    }

    public BigDecimal calculateByRevisedHarrisBenedictFormulaForFemale(BigDecimal weight, BigDecimal height, Integer age) {
        final BigDecimal femaleWeightFactor = BigDecimal.valueOf(9.25);
        final BigDecimal femaleHeightFactor = BigDecimal.valueOf(310);
        final BigDecimal femaleAgeFactor = BigDecimal.valueOf(4.33);
        final BigDecimal femaleAdditiveFactor = BigDecimal.valueOf(447.6);

        BigDecimal weightContribution = femaleWeightFactor.multiply(weight);
        BigDecimal heightContribution = femaleHeightFactor.multiply(height);
        BigDecimal ageContribution = femaleAgeFactor.multiply(BigDecimal.valueOf(age));

        return femaleAdditiveFactor
                .add(weightContribution)
                .add(heightContribution)
                .subtract(ageContribution)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateByRevisedHarrisBenedictFormulaForMale(BigDecimal weight, BigDecimal height, Integer age) {
        final BigDecimal maleWeightFactor = BigDecimal.valueOf(13.4);
        final BigDecimal maleHeightFactor = BigDecimal.valueOf(480);
        final BigDecimal maleAgeFactor = BigDecimal.valueOf(5.68);
        final BigDecimal maleAdditiveFactor = BigDecimal.valueOf(88.4);

        BigDecimal weightContribution = maleWeightFactor.multiply(weight);
        BigDecimal heightContribution = maleHeightFactor.multiply(height);
        BigDecimal ageContribution = maleAgeFactor.multiply(BigDecimal.valueOf(age));

        return maleAdditiveFactor
                .add(weightContribution)
                .add(heightContribution)
                .subtract(ageContribution)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
