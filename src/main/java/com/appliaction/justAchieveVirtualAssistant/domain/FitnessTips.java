package com.appliaction.justAchieveVirtualAssistant.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = "tipId")
@ToString(of = {"tip"})
public class FitnessTips {

    Integer tipId;
    DietGoals dietGoalId;
    String tip;
}
