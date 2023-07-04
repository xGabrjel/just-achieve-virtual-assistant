package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa;

import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.BodyMeasurementsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BodyMeasurementsJpaRepository extends JpaRepository<BodyMeasurementsEntity, Integer> {
    Optional<BodyMeasurementsEntity> findByDateAndProfileId(LocalDate date, UserProfileEntity userProfileEntity);
}
