package com.appliaction.justAchieveVirtualAssistant.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@With
@Value
@Builder
@EqualsAndHashCode(of = "bodyMeasurementId")
@ToString(of = {"date", "currentWeight", "calf", "thigh", "waist", "chest", "arm", "measurementNote"})
public class BodyMeasurements {

    Integer bodyMeasurementId;
    UserProfile profileId;
    LocalDate date;
    BigDecimal currentWeight;
    BigDecimal calf;
    BigDecimal thigh;
    BigDecimal waist;
    BigDecimal chest;
    BigDecimal arm;
    String measurementNote;
}
