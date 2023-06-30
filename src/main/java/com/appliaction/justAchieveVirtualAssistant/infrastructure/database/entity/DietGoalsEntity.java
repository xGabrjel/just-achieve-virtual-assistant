package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dietGoal")
    private Set<UserProfileEntity> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dietGoal")
    private Set<FitnessTipsEntity> tips;
}
