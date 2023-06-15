package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity;

import com.appliaction.justAchieveVirtualAssistant.security.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profile")
@EqualsAndHashCode(of = "profileId")
@ToString(of = {"profileId", "userId", "name", "surname", "phone", "age", "sex"})
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", unique = true, nullable = false)
    private Integer profileId;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserEntity userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Length(min = 7, max = 15)
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "weight", nullable = false)
    private BigDecimal weight;

    @Column(name = "height", nullable = false)
    private Integer height;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_goal_id")
    private DietGoalsEntity dietGoalId;
}

