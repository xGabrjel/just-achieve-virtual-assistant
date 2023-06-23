package com.appliaction.justAchieveVirtualAssistant.api.dto.mapper;

import com.appliaction.justAchieveVirtualAssistant.api.dto.BodyMeasurementsDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BodyMeasurementsMapper {

    BodyMeasurementsDTO map(BodyMeasurements bodyMeasurements);
}
