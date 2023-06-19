package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa;

import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.BodyMeasurementsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface BodyMeasurementsJpaRepository extends JpaRepository<BodyMeasurementsEntity, Integer> {
    List<BodyMeasurementsEntity> findByDateAndProfileId(OffsetDateTime offsetDateTime, UserProfileEntity userProfileEntity);
}
