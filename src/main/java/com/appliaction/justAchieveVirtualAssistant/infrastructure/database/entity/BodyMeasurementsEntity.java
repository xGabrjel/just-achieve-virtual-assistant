package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity;

import com.appliaction.justAchieveVirtualAssistant.security.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

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

    @JoinColumn(name = "email", nullable = false)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserEntity email;

    @Column(name = "date", nullable = false)
    private OffsetDateTime date;

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
