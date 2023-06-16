package com.appliaction.justAchieveVirtualAssistant.util;

import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.BodyMeasurementsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.DietGoalsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FitnessTipsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.security.UserEntity;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@UtilityClass
public class EntityMapperFixtures {
    public static DietGoalsEntity someDietGoals() {
        return DietGoalsEntity.builder()
                .dietGoalId(2)
                .dietGoal("BUILDING MUSCLE")
                .users(Set.of())
                .tips(Set.of())
                .build();
    }

    public static FitnessTipsEntity someFitnessTips() {
        return FitnessTipsEntity.builder()
                .tipId(1)
                .tip("Eat a lot of protein!")
                .build();
    }

    public static UserEntity someUser() {
        return UserEntity.builder()
                .userId(1)
                .email("test@test.pl")
                .active(true)
                .build();
    }

    public static UserProfileEntity someUserProfile() {
        return UserProfileEntity.builder()
                .profileId(1)
                .name("Adam")
                .surname("Adamski")
                .phone("+48 511 522 533")
                .age(54)
                .sex("MALE")
                .weight(new BigDecimal("77"))
                .height(BigDecimal.valueOf(1.83))
                .build();
    }

    public static BodyMeasurementsEntity someBodyMeasurements() {
        return BodyMeasurementsEntity.builder()
                .bodyMeasurementId(1)
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
