package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.BodyMeasurementsRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class BodyMeasurementsService {

    private final BodyMeasurementsRepository bodyMeasurementsRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileJpaRepository userProfileJpaRepository;

    @Transactional
    public void saveBodyMeasurements(BodyMeasurements bodyMeasurements) {
        log.info("BodyMeasurements to save: [%s]".formatted(bodyMeasurements));
        bodyMeasurementsRepository.saveBodyMeasurements(bodyMeasurements);
    }

    @Transactional
    public void updateUserProfileWeight(BodyMeasurements bodyMeasurements, String phoneNumber) {
        UserProfile userProfile = userProfileRepository.getUserProfile(phoneNumber);

        userProfileJpaRepository.updateWeightByProfileId(userProfile.getProfileId(), bodyMeasurements.getCurrentWeight());

        log.info("UserProfile current weight: [%s] was updated with new weight: [%s]"
                .formatted(userProfile.getWeight(), bodyMeasurements.getCurrentWeight()));
    }
}
