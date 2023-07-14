package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"tip"})
@Table(name = "fitness_tips")
@EqualsAndHashCode(of = "tipId")
public class FitnessTipsEntity {

    @Id
    @Column(name = "tip_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tipId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "diet_goal_id")
    private DietGoalsEntity dietGoal;

    @Column(name = "tip", nullable = false)
    private String tip;
}
