package com.appliaction.justAchieveVirtualAssistant.api.dto.mapper;

import com.appliaction.justAchieveVirtualAssistant.api.dto.DietGoalsDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DietGoalsMapper {

    DietGoalsDTO map(DietGoals bodyMeasurements);
}
