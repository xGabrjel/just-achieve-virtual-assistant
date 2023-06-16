package com.appliaction.justAchieveVirtualAssistant.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode(of = "profileId")
@ToString(of = {"profileId", "name", "surname", "phone", "age", "sex", "weight", "height"})
public class UserProfile {

    Integer profileId;
    User user;
    String name;
    String surname;
    String phone;
    Integer age;
    String sex;
    BigDecimal weight;
    Integer height;
    DietGoals dietGoal;
}
