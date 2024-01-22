package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.api.dto.BodyMeasurementsDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.BodyMeasurementsMapper;
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
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@AllArgsConstructor
public class BodyMeasurementsService {

    private final BodyMeasurementsRepository bodyMeasurementsRepository;
    private final BodyMeasurementsJpaRepository bodyMeasurementsJpaRepository;
    private final UserProfileEntityMapper userProfileEntityMapper;

    private final BodyMeasurementsMapper bodyMeasurementsMapper;
    private final UserProfileService userProfileService;

    private boolean isPresent(BodyMeasurements bodyMeasurements, UserProfile userProfile) {
        return bodyMeasurementsJpaRepository
                .findByDateAndProfileId(bodyMeasurements.getDate(), userProfileEntityMapper.mapToEntity(userProfile)).isPresent();
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

    public BodyMeasurementsDTO findFinalBodyMeasurementsDTO(String date, String username) {
        return bodyMeasurementsMapper.map(findByDateAndProfileId(
                LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE),
                userProfileService.findByUsername(username)
        ));
    }

    @Transactional
    public void saveBodyMeasurements(BodyMeasurementsDTO bodyMeasurementsDTO, String username) {
        UserProfile user = userProfileService.findByUsername(username);

        BodyMeasurements toSave = BodyMeasurements.builder()
                .profileId(user)
                .date(bodyMeasurementsDTO.getDate())
                .currentWeight(bodyMeasurementsDTO.getCurrentWeight())
                .calf(bodyMeasurementsDTO.getCalf())
                .thigh(bodyMeasurementsDTO.getThigh())
                .waist(bodyMeasurementsDTO.getWaist())
                .chest(bodyMeasurementsDTO.getChest())
                .arm(bodyMeasurementsDTO.getArm())
                .measurementNote(bodyMeasurementsDTO.getMeasurementNote())
                .build();

        if (isPresent(toSave, toSave.getProfileId())) {
            bodyMeasurementsRepository.delete(findByDateAndProfileId(toSave.getDate(), toSave.getProfileId()));
        }
        log.info("Body measurements to save: [%s]: ".formatted(toSave));
        bodyMeasurementsRepository.saveBodyMeasurements(toSave);
    }
}
