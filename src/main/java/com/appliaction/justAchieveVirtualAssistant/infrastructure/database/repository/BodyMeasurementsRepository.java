package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository;

import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.BodyMeasurementsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.BodyMeasurementsEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.UserProfileEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.BodyMeasurementsJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Repository
@AllArgsConstructor
public class BodyMeasurementsRepository {

    private final BodyMeasurementsJpaRepository bodyMeasurementsJpaRepository;
    private final BodyMeasurementsEntityMapper bodyMeasurementsEntityMapper;
    private final UserProfileEntityMapper userProfileEntityMapper;
    private final UserProfileJpaRepository userProfileJpaRepository;

    @Transactional
    public void saveBodyMeasurements(BodyMeasurements bodyMeasurements) {
        BodyMeasurementsEntity toSave = bodyMeasurementsEntityMapper.mapToEntity(bodyMeasurements);
        bodyMeasurementsJpaRepository.save(toSave);
        updateUserProfileWeight(bodyMeasurements);
    }

    @Transactional
    public void updateUserProfileWeight(BodyMeasurements bodyMeasurements) {
        userProfileJpaRepository.updateWeightByProfileId(bodyMeasurements.getProfileId().getProfileId(), bodyMeasurements.getCurrentWeight());
    }

    public BodyMeasurements findByDateAndProfileId(LocalDate date, UserProfile userProfile) {
        UserProfileEntity userProfileEntity = userProfileEntityMapper.mapToEntity(userProfile);
        return bodyMeasurementsJpaRepository.findByDateAndProfileId(date, userProfileEntity).stream()
                .map(bodyMeasurementsEntityMapper::mapFromEntity)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Could not find BodyMeasurements by this date: [%s]".formatted(date)));
    }

    public void delete(BodyMeasurements bodyMeasurements) {
        BodyMeasurementsEntity entity = bodyMeasurementsEntityMapper.mapToEntity(bodyMeasurements);
        bodyMeasurementsJpaRepository.delete(entity);
    }
}
