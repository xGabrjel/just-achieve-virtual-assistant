package com.appliaction.justAchieveVirtualAssistant.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyMeasurementsDTO {

    private OffsetDateTime date;
    private BigDecimal currentWeight;
    private BigDecimal calf;
    private BigDecimal thigh;
    private BigDecimal waist;
    private BigDecimal chest;
    private BigDecimal arm;
    private String measurementNote;
}
