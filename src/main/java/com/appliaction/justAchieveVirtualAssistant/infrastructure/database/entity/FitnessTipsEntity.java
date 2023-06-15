package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fitness_tips")
@EqualsAndHashCode(of = "tipId")
@ToString(of = {"tip"})
public class FitnessTipsEntity {

    @Id
    @Column(name = "tip_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tipId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_goal_id")
    private DietGoalsEntity dietGoalId;

    @Column(name = "tip", nullable = false)
    private String tip;
}
