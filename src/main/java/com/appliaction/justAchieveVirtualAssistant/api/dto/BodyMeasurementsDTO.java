package com.appliaction.justAchieveVirtualAssistant.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyMeasurementsDTO {

    private LocalDate date;
    @Min(1)
    @Max(200)
    private BigDecimal currentWeight;
    private BigDecimal calf;
    private BigDecimal thigh;
    private BigDecimal waist;
    private BigDecimal chest;
    private BigDecimal arm;
    @Size(max = 500)
    private String measurementNote;
}
