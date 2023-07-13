package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity;

import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "food")
@EqualsAndHashCode(of = "foodId")
@ToString(of = {"foodId", "name", "servingSizeG"})
public class FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id", nullable = false)
    private Integer foodId;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private UserEntity userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "calories", nullable = false)
    private BigDecimal calories;

    @Column(name = "serving_size_g", nullable = false)
    private Integer servingSizeG;

    @Column(name = "fat_total_g", nullable = false)
    private BigDecimal fatTotalG;

    @Column(name = "fat_saturated_g", nullable = false)
    private BigDecimal fatSaturatedG;

    @Column(name = "protein_g", nullable = false)
    private BigDecimal proteinG;

    @Column(name = "sodium_mg", nullable = false)
    private BigDecimal sodiumMg;

    @Column(name = "potassium_mg", nullable = false)
    private BigDecimal potassiumMg;

    @Column(name = "cholesterol_mg", nullable = false)
    private BigDecimal cholesterolMg;

    @Column(name = "carbohydrates_total_g", nullable = false)
    private BigDecimal carbohydratesTotalG;

    @Column(name = "fiber_g", nullable = false)
    private BigDecimal fiberG;

    @Column(name = "sugar_g", nullable = false)
    private BigDecimal sugarG;
}
