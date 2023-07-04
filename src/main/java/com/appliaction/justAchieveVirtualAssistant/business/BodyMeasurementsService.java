package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.UserProfileEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.BodyMeasurementsRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.BodyMeasurementsJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor
public class BodyMeasurementsService {

    private final BodyMeasurementsRepository bodyMeasurementsRepository;
    private final BodyMeasurementsJpaRepository bodyMeasurementsJpaRepository;
    private final UserProfileEntityMapper userProfileEntityMapper;

    @Transactional
    public void saveBodyMeasurements(BodyMeasurements bodyMeasurements) {
        UserProfile userProfile = bodyMeasurements.getProfileId();

        if (bodyMeasurementsJpaRepository
                .findByDateAndProfileId(bodyMeasurements.getDate(), userProfileEntityMapper.mapToEntity(userProfile)).isPresent()) {
            bodyMeasurementsRepository.delete(findByDateAndProfileId(bodyMeasurements.getDate(), bodyMeasurements.getProfileId()));
        }
        log.info("Body measurements to save: [%s]: ".formatted(bodyMeasurements));
        bodyMeasurementsRepository.saveBodyMeasurements(bodyMeasurements);
    }

    @Transactional
    public void updateUserProfileWeight(BodyMeasurements bodyMeasurements) {
        log.info("UserProfile current weight: [%s] is updating with new weight: [%s]"
                .formatted(bodyMeasurements.getProfileId().getWeight(), bodyMeasurements.getCurrentWeight()));

        bodyMeasurementsRepository.updateUserProfileWeight(bodyMeasurements);
    }

    public BodyMeasurements findByDateAndProfileId(LocalDate date, UserProfile userProfile) {
        log.info("Finding body measurements by: Date: [%s], UserProfile: [%s]".formatted(date, userProfile));

        return bodyMeasurementsRepository.findByDateAndProfileId(date, userProfile);
    }

}
