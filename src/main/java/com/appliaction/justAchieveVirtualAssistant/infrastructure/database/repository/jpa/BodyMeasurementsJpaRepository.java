package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa;

import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.BodyMeasurementsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyMeasurementsJpaRepository extends JpaRepository<BodyMeasurementsEntity, Integer> {
}
