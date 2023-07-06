package com.appliaction.justAchieveVirtualAssistant.util;

import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.BodyMeasurementsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.DietGoalsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FitnessTipsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.RoleEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@UtilityClass
public class EntityFixtures {
    public static DietGoalsEntity someDietGoalsEntity() {
        return DietGoalsEntity.builder()
                .dietGoalId(2)
                .dietGoal("BUILDING MUSCLE")
                .build();
    }

    public static FitnessTipsEntity someFitnessTipsEntity() {
        return FitnessTipsEntity.builder()
                .tipId(1)
                .tip("Eat a lot of protein!")
                .build();
    }

    public static UserEntity someUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1);
        userEntity.setUsername("test1");
        userEntity.setPassword("test1");
        userEntity.setEmail("test@test.pl");
        userEntity.setActive(true);
        userEntity.setRoles(List.of(new RoleEntity("USER")));

        return userEntity;
    }

    public static UserProfileEntity someUserProfileEntity() {
        return UserProfileEntity.builder()
                .profileId(1)
                .user(someUserEntity())
                .name("Adam")
                .surname("Adamski")
                .phone("+48 511 522 533")
                .age(54)
                .sex("MALE")
                .weight(new BigDecimal("77"))
                .dietGoal(someDietGoalsEntity())
                .height(BigDecimal.valueOf(1.83))
                .build();
    }

    public static BodyMeasurementsEntity someBodyMeasurementsEntity() {
        return BodyMeasurementsEntity.builder()
                .bodyMeasurementId(1)
                .profileId(someUserProfileEntity())
                .date(LocalDate.now())
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
