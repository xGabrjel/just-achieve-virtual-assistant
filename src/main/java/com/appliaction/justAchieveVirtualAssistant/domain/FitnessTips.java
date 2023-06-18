package com.appliaction.justAchieveVirtualAssistant.domain;

import lombok.*;

@With
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "tipId")
@ToString(of = {"tip"})
public class FitnessTips {

    private Integer tipId;
    private DietGoals dietGoal;
    private String tip;
}
