package com.appliaction.justAchieveVirtualAssistant.util;

import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
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
        return User.builder()
                .userId(1)
                .username("test1")
                .password("test1")
                .email("test@test.pl")
                .active(true)
                .build();
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
}
