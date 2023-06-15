package com.appliaction.justAchieveVirtualAssistant.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@With
@Value
@Builder
@EqualsAndHashCode(of = "bodyMeasurementId")
@ToString(of = {"date", "currentWeight", "calf", "thigh", "waist", "chest", "arm", "measurementNote"})
public class BodyMeasurements {

    Integer bodyMeasurementId;
    User userId;
    OffsetDateTime date;
    BigDecimal currentWeight;
    BigDecimal calf;
    BigDecimal thigh;
    BigDecimal waist;
    BigDecimal chest;
    BigDecimal arm;
    String measurementNote;
}
