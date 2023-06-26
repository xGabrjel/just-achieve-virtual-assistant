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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class BodyMeasurementsRepository {

    private final BodyMeasurementsJpaRepository bodyMeasurementsJpaRepository;
    private final BodyMeasurementsEntityMapper bodyMeasurementsEntityMapper;
    private final UserProfileEntityMapper userProfileEntityMapper;
    private final UserProfileJpaRepository userProfileJpaRepository;

    @Transactional
    public void saveBodyMeasurements(BodyMeasurements bodyMeasurements) {
        log.info("BodyMeasurements to save: [%s]".formatted(bodyMeasurements));
        BodyMeasurementsEntity toSave = bodyMeasurementsEntityMapper.mapToEntity(bodyMeasurements);

        bodyMeasurementsJpaRepository.save(toSave);
        updateUserProfileWeight(bodyMeasurements);
    }

    @Transactional
    public void updateUserProfileWeight(BodyMeasurements bodyMeasurements) {
        userProfileJpaRepository.updateWeightByProfileId(bodyMeasurements.getProfileId().getProfileId(), bodyMeasurements.getCurrentWeight());

        log.info("UserProfile current weight: [%s] was updated with new weight: [%s]"
                .formatted(bodyMeasurements.getProfileId().getWeight(), bodyMeasurements.getCurrentWeight()));
    }

    public List<BodyMeasurements> findByDateAndProfileId(OffsetDateTime offsetDateTime, UserProfile userProfile) {
        log.info("Finding body measurements by: Date: [%s], UserProfile: [%s]".formatted(offsetDateTime, userProfile));

        UserProfileEntity userProfileEntity = userProfileEntityMapper.mapToEntity(userProfile);
        List<BodyMeasurements> list = bodyMeasurementsJpaRepository.findByDateAndProfileId(offsetDateTime, userProfileEntity).stream()
                .map(bodyMeasurementsEntityMapper::mapFromEntity)
                .toList();
        return list;
    }
}
