package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity;

import com.appliaction.justAchieveVirtualAssistant.security.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profile")
@EqualsAndHashCode(of = "profileId")
@ToString(of = {"profileId", "name", "surname", "phone", "age", "sex", "weight", "height"})
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", unique = true, nullable = false)
    private Integer profileId;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "weight", nullable = false)
    private BigDecimal weight;

    @Column(name = "height", nullable = false)
    private BigDecimal height;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_goal_id")
    private DietGoalsEntity dietGoal;
}

