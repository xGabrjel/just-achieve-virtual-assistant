package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa;

import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FitnessTipsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FitnessTipsJpaRepository extends JpaRepository<FitnessTipsEntity, Integer> {
    List<FitnessTipsEntity> findByDietGoalDietGoalId(Integer dietGoalId);
}
