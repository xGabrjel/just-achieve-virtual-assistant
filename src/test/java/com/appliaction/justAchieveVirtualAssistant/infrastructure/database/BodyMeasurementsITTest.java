package com.appliaction.justAchieveVirtualAssistant.infrastructure.database;

import com.appliaction.justAchieveVirtualAssistant.business.BodyMeasurementsService;
import com.appliaction.justAchieveVirtualAssistant.configuration.AbstractIT;
import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@AllArgsConstructor(onConstructor = @__(@Autowired))
class BodyMeasurementsITTest extends AbstractIT {

    private final BodyMeasurementsService bodyMeasurementsService;
    private final UserProfileJpaRepository userProfileJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Test
    void saveAndFindBodyMeasurementsWorksCorrectly() {
        //given
        BodyMeasurements bodyMeasurements = DomainFixtures.someBodyMeasurements();
        UserEntity userEntity = EntityFixtures.someUserEntity();
        UserProfileEntity userProfileEntity = EntityFixtures.someUserProfileEntity();

        userJpaRepository.saveAndFlush(userEntity);
        userProfileJpaRepository.saveAndFlush(userProfileEntity);
        bodyMeasurementsService.saveBodyMeasurements(bodyMeasurements);

        //when
        var availableBodyMeasurements = bodyMeasurementsService
                .findByDateAndProfileId(bodyMeasurements.getDate(), bodyMeasurements.getProfileId());

        //then
        assertThat(availableBodyMeasurements).isNotNull();
        assertThat(availableBodyMeasurements).isEqualTo(bodyMeasurements);
    }

    @Test
    void findEmptyBodyMeasurementsWorksCorrectly() {
        //given
        BodyMeasurements bodyMeasurements = DomainFixtures.someBodyMeasurements();
        UserEntity userEntity = EntityFixtures.someUserEntity();
        UserProfileEntity userProfileEntity = EntityFixtures.someUserProfileEntity();

        userJpaRepository.saveAndFlush(userEntity);
        userProfileJpaRepository.saveAndFlush(userProfileEntity);

        //when, then
        assertThatThrownBy(() ->
                bodyMeasurementsService.findByDateAndProfileId(bodyMeasurements.getDate(), bodyMeasurements.getProfileId()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateUserProfileWeightWorksCorrectly() {
        //given
        BodyMeasurements bodyMeasurements = DomainFixtures.someBodyMeasurements();
        UserEntity userEntity = EntityFixtures.someUserEntity();
        UserProfileEntity userProfileEntity = EntityFixtures.someUserProfileEntity();

        userJpaRepository.saveAndFlush(userEntity);
        userProfileJpaRepository.saveAndFlush(userProfileEntity);
        bodyMeasurementsService.saveBodyMeasurements(bodyMeasurements);
        bodyMeasurementsService.updateUserProfileWeight(bodyMeasurements);

        //when
        UserProfileEntity updatedUserProfileEntity = userProfileJpaRepository.findById(bodyMeasurements.getProfileId().getProfileId()).orElse(null);

        //then
        assertThat(bodyMeasurements.getCurrentWeight()).isEqualTo(Objects.requireNonNull(updatedUserProfileEntity).getWeight());
        assertThat(updatedUserProfileEntity).isNotNull();
    }
}