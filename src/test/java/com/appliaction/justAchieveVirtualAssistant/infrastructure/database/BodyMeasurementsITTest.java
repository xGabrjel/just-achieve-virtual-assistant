package com.appliaction.justAchieveVirtualAssistant.infrastructure.database;

import com.appliaction.justAchieveVirtualAssistant.business.BodyMeasurementsService;
import com.appliaction.justAchieveVirtualAssistant.configuration.AbstractIT;
import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class BodyMeasurementsITTest extends AbstractIT {

    private final BodyMeasurementsService bodyMeasurementsService;
    private final UserProfileJpaRepository userProfileJpaRepository;

    @Test
    void saveAndFindBodyMeasurementsWorksCorrectly() {
        //given
        BodyMeasurements bodyMeasurements1 = DomainFixtures.someBodyMeasurements();

        //when
        bodyMeasurementsService.saveBodyMeasurements(bodyMeasurements1);
        var availableBodyMeasurements1 = bodyMeasurementsService
                .findByDateAndProfileId(bodyMeasurements1.getDate(), bodyMeasurements1.getProfileId());

        //then
        assertThat(availableBodyMeasurements1).isNotNull();
        assertThat(availableBodyMeasurements1).isNotEmpty();
        assertThat(availableBodyMeasurements1).hasSize(1);
    }

    @Test
    void findEmptyBodyMeasurementsWorksCorrectly() {
        //given
        BodyMeasurements bodyMeasurements2 = DomainFixtures.someBodyMeasurements();

        //when
        var availableBodyMeasurements2 = bodyMeasurementsService
                .findByDateAndProfileId(bodyMeasurements2.getDate(), bodyMeasurements2.getProfileId());

        //then
        assertThat(availableBodyMeasurements2).isNotNull();
        assertThat(availableBodyMeasurements2).hasSize(0);
        assertThat(availableBodyMeasurements2).isEmpty();
    }

    @Test
    void updateUserProfileWeightWorksCorrectly() {
        //given
        BodyMeasurements bodyMeasurements = DomainFixtures.someBodyMeasurements();
        UserProfile userProfile = DomainFixtures.someUserProfile();

        //when
        bodyMeasurementsService.updateUserProfileWeight(bodyMeasurements);
        UserProfileEntity updatedUserProfileEntity = userProfileJpaRepository.findById(userProfile.getProfileId()).orElse(null);

        //then
        assertThat(updatedUserProfileEntity).isNotNull();
        assertThat(bodyMeasurements.getCurrentWeight()).isEqualTo(Objects.requireNonNull(updatedUserProfileEntity).getWeight());
    }
}