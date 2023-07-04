package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "body_measurements")
@EqualsAndHashCode(of = "bodyMeasurementId")
@ToString(of = {"date", "currentWeight", "calf", "thigh", "waist", "chest", "arm", "measurementNote"})
public class BodyMeasurementsEntity {

    @Id
    @Column(name = "body_measurement_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bodyMeasurementId;

    @JoinColumn(name = "profile_id", nullable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private UserProfileEntity profileId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "current_weight", nullable = false)
    private BigDecimal currentWeight;

    @Column(name = "calf", nullable = false)
    private BigDecimal calf;

    @Column(name = "thigh", nullable = false)
    private BigDecimal thigh;

    @Column(name = "waist", nullable = false)
    private BigDecimal waist;

    @Column(name = "chest", nullable = false)
    private BigDecimal chest;

    @Column(name = "arm", nullable = false)
    private BigDecimal arm;

    @Column(name = "measurement_note")
    private String measurementNote;
}
