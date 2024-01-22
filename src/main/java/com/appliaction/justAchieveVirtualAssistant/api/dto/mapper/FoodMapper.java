package com.appliaction.justAchieveVirtualAssistant.api.dto.mapper;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    FoodDTO map(Food food);
}
