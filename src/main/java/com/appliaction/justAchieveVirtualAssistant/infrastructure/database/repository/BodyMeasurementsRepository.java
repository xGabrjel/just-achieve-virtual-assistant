package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository;

import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.BodyMeasurementsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.BodyMeasurementsEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.UserProfileEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.BodyMeasurementsJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;


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

    public List<BodyMeasurementsEntity> findByDateAndProfileId(OffsetDateTime offsetDateTime, UserProfile userProfile) {
        UserProfileEntity userProfileEntity = userProfileEntityMapper.mapToEntity(userProfile);
        return bodyMeasurementsJpaRepository.findByDateAndProfileId(offsetDateTime, userProfileEntity);
    }
}
