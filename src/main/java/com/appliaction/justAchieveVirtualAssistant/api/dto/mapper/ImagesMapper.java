package com.appliaction.justAchieveVirtualAssistant.api.dto.mapper;

import com.appliaction.justAchieveVirtualAssistant.api.dto.ImagesDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImagesMapper {

    ImagesDTO map(Images bodyMeasurements);
}
