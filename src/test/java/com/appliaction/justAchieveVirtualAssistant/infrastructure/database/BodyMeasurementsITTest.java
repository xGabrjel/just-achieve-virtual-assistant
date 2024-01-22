package com.appliaction.justAchieveVirtualAssistant.infrastructure.database;

import com.appliaction.justAchieveVirtualAssistant.api.dto.BodyMeasurementsDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.BodyMeasurementsMapper;
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

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@AllArgsConstructor(onConstructor = @__(@Autowired))
class BodyMeasurementsITTest extends AbstractIT {

    private final BodyMeasurementsService bodyMeasurementsService;
    private final UserProfileJpaRepository userProfileJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final BodyMeasurementsMapper bodyMeasurementsMapper;

    @Test
    void saveAndFindBodyMeasurementsWorksCorrectly12() {
        //given
        BodyMeasurements bodyMeasurements = DomainFixtures.someBodyMeasurements();
        BodyMeasurementsDTO bodyMeasurementsDTO = bodyMeasurementsMapper.map(bodyMeasurements);
        UserEntity userEntity = EntityFixtures.someUserEntity();
        UserProfileEntity userProfileEntity = EntityFixtures.someUserProfileEntity();
        String date = bodyMeasurementsDTO.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE);

        userJpaRepository.saveAndFlush(userEntity);
        userProfileJpaRepository.saveAndFlush(userProfileEntity);
        bodyMeasurementsService.saveBodyMeasurements(bodyMeasurementsDTO, userEntity.getUsername());

        //when
        var availableBodyMeasurementsDTO = bodyMeasurementsService
                .findFinalBodyMeasurementsDTO(date, userEntity.getUsername());

        //then
        assertThat(availableBodyMeasurementsDTO).isNotNull();
        assertThat(availableBodyMeasurementsDTO.getMeasurementNote()).isEqualTo(bodyMeasurementsDTO.getMeasurementNote());
        assertThat(availableBodyMeasurementsDTO.getDate()).isEqualTo(bodyMeasurementsDTO.getDate());
        assertThat(availableBodyMeasurementsDTO.getArm()).isEqualTo(bodyMeasurementsDTO.getArm());
        assertThat(availableBodyMeasurementsDTO.getCalf()).isEqualTo(bodyMeasurementsDTO.getCalf());
        assertThat(availableBodyMeasurementsDTO.getChest()).isEqualTo(bodyMeasurementsDTO.getChest());
        assertThat(availableBodyMeasurementsDTO.getCurrentWeight()).isEqualTo(bodyMeasurementsDTO.getCurrentWeight());
        assertThat(availableBodyMeasurementsDTO.getThigh()).isEqualTo(bodyMeasurementsDTO.getThigh());
        assertThat(availableBodyMeasurementsDTO.getWaist()).isEqualTo(bodyMeasurementsDTO.getWaist());
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
        BodyMeasurementsDTO bodyMeasurementsDTO = bodyMeasurementsMapper.map(bodyMeasurements);
        UserEntity userEntity = EntityFixtures.someUserEntity();
        UserProfileEntity userProfileEntity = EntityFixtures.someUserProfileEntity();

        userJpaRepository.saveAndFlush(userEntity);
        userProfileJpaRepository.saveAndFlush(userProfileEntity);
        bodyMeasurementsService.saveBodyMeasurements(bodyMeasurementsDTO, userEntity.getUsername());
        bodyMeasurementsService.updateUserProfileWeight(bodyMeasurements);

        //when
        UserProfileEntity updatedUserProfileEntity = userProfileJpaRepository
                .findById(bodyMeasurements.getProfileId().getProfileId()).orElse(null);

        //then
        assertThat(bodyMeasurements.getCurrentWeight()).isEqualTo(Objects.requireNonNull(updatedUserProfileEntity).getWeight());
        assertThat(updatedUserProfileEntity).isNotNull();
    }
}