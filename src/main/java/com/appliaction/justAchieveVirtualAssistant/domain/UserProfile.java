package com.appliaction.justAchieveVirtualAssistant.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "profileId")
@ToString(of = {"profileId", "name", "surname", "phone", "age", "sex", "weight", "height"})
public class UserProfile {

    private Integer profileId;
    private User user;
    private String name;
    private String surname;
    private String phone;
    private Integer age;
    private String sex;
    private BigDecimal weight;
    private BigDecimal height;
    private DietGoals dietGoal;
}
