package com.appliaction.justAchieveVirtualAssistant.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "dietGoalId")
@ToString(of = {"dietGoalId", "dietGoal"})
public class DietGoals {

    Integer dietGoalId;
    String dietGoal;
    Set<UserProfile> users;
    Set<FitnessTips> tips;
}
