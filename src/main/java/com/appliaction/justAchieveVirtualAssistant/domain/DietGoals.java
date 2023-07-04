package com.appliaction.justAchieveVirtualAssistant.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = "dietGoalId")
@ToString(of = {"dietGoalId", "dietGoal"})
public class DietGoals {

    Integer dietGoalId;
    String dietGoal;
}
