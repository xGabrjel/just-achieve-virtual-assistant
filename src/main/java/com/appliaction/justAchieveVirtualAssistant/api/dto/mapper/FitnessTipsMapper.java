package com.appliaction.justAchieveVirtualAssistant.api.dto.mapper;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FitnessTipsDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.FitnessTips;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FitnessTipsMapper {

    FitnessTipsDTO map(FitnessTips bodyMeasurements);
}
