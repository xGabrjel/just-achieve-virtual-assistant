package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.configuration.AbstractIT;
import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.BodyMeasurementsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.BodyMeasurementsRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class BodyMeasurementsServiceTest extends AbstractIT {

    private final BodyMeasurementsRepository bodyMeasurementsRepository;
    private final UserProfileJpaRepository userProfileJpaRepository;

    @Test
    void saveAndFindBodyMeasurementsWorksCorrectly() {
        //given
        BodyMeasurements bodyMeasurements1 = DomainFixtures.someBodyMeasurements();
        BodyMeasurements bodyMeasurements2 = DomainFixtures.someBodyMeasurements();

        //when
        bodyMeasurementsRepository.saveBodyMeasurements(bodyMeasurements1);
        List<BodyMeasurementsEntity> availableBodyMeasurements1 = bodyMeasurementsRepository
                .findByDateAndProfileId(bodyMeasurements1.getDate(), bodyMeasurements1.getProfileId());

        bodyMeasurementsRepository.saveBodyMeasurements(bodyMeasurements2);
        List<BodyMeasurementsEntity> availableBodyMeasurements2 = bodyMeasurementsRepository
                .findByDateAndProfileId(bodyMeasurements2.getDate(), bodyMeasurements2.getProfileId());

        //then
        assertThat(availableBodyMeasurements1).isNotNull();
        assertThat(availableBodyMeasurements1).isNotEmpty();
        assertThat(availableBodyMeasurements1).hasSize(1);

        assertThat(availableBodyMeasurements2).isNotNull();
        assertThat(availableBodyMeasurements2).isNotEmpty();
        assertThat(availableBodyMeasurements2).hasSize(1);
    }

    @Test
    void updateUserProfileWeightWorksCorrectly() {
        //given
        BodyMeasurements bodyMeasurements = DomainFixtures.someBodyMeasurements();
        UserProfile userProfile = DomainFixtures.someUserProfile();

        //when
        bodyMeasurementsRepository.updateUserProfileWeight(bodyMeasurements);
        UserProfileEntity updatedUserProfileEntity = userProfileJpaRepository.findById(userProfile.getProfileId()).orElse(null);

        //then
        assertThat(updatedUserProfileEntity).isNotNull();
        assertThat(bodyMeasurements.getCurrentWeight()).isEqualTo(Objects.requireNonNull(updatedUserProfileEntity).getWeight());
    }
}