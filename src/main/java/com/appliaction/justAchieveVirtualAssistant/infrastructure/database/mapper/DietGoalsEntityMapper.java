package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.DietGoalsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DietGoalsEntityMapper {

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "tips", ignore = true)
    DietGoals mapFromEntity(DietGoalsEntity entity);
}
