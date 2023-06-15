package com.appliaction.justAchieveVirtualAssistant.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode(of = "profileId")
@ToString(of = {"profileId", "userId", "name", "surname", "phone", "age", "sex"})
public class UserProfile {

    Integer profileId;
    User userId;
    String name;
    String surname;
    String phone;
    Integer age;
    String sex;
    BigDecimal weight;
    Integer height;
    DietGoals dietGoalId;
}
