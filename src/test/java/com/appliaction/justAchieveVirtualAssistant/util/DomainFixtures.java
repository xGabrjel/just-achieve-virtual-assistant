package com.appliaction.justAchieveVirtualAssistant.util;

import com.appliaction.justAchieveVirtualAssistant.domain.*;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@UtilityClass
public class DomainFixtures {
    public static UserProfile someUserProfile() {
        return UserProfile.builder()
                .profileId(1)
                .user(someUser())
                .name("Adam")
                .surname("Adamski")
                .phone("+48 511 522 533")
                .age(54)
                .sex("MALE")
                .weight(new BigDecimal("77"))
                .dietGoal(someDietGoals())
                .height(BigDecimal.valueOf(1.83))
                .build();
    }

    public static User someUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("test1");
        user.setPassword("test1");
        user.setEmail("test@test.pl");
        user.setActive(true);

        return user;
    }

    public static DietGoals someDietGoals() {
        return DietGoals.builder()
                .dietGoalId(2)
                .dietGoal("BUILDING MUSCLE")
                .build();
    }

    public static BodyMeasurements someBodyMeasurements() {
        return BodyMeasurements.builder()
                .bodyMeasurementId(1)
                .profileId(someUserProfile())
                .date(OffsetDateTime.now())
                .currentWeight(new BigDecimal("100.3"))
                .calf(new BigDecimal("40.3"))
                .thigh(new BigDecimal("70.2"))
                .waist(new BigDecimal("92.1"))
                .chest(new BigDecimal("118.2"))
                .arm(new BigDecimal("44.5"))
                .measurementNote("Random values for testing purpose")
                .build();
    }

    public static FitnessTips someFitnessTips() {
        return FitnessTips.builder()
                .tipId(1)
                .tip("Eat a lot of protein!")
                .build();
    }
}
