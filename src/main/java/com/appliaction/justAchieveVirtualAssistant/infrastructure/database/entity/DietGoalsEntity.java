package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diet_goals")
@EqualsAndHashCode(of = "dietGoalId")
@ToString(of = {"dietGoalId", "dietGoal"})
public class DietGoalsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_goal_id", unique = true, nullable = false)
    private Integer dietGoalId;

    @Column(name = "diet_goal", unique = true, nullable = false)
    private String dietGoal;
}
