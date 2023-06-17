package com.appliaction.justAchieveVirtualAssistant.util;

import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;

import java.math.BigDecimal;

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
}
