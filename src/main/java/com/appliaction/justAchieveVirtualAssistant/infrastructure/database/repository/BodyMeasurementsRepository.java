package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository;

import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.BodyMeasurementsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.BodyMeasurementsEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.BodyMeasurementsJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class BodyMeasurementsRepository {

    private final BodyMeasurementsJpaRepository bodyMeasurementsJpaRepository;
    private final BodyMeasurementsEntityMapper bodyMeasurementsEntityMapper;

    public void saveBodyMeasurements(BodyMeasurements bodyMeasurements) {
        BodyMeasurementsEntity toSave = bodyMeasurementsEntityMapper.mapToEntity(bodyMeasurements);
        bodyMeasurementsJpaRepository.save(toSave);
    }
}
