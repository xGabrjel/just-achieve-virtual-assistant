package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa;

import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.DietGoalsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietGoalsJpaRepository extends JpaRepository<DietGoalsEntity, Integer> {
}
