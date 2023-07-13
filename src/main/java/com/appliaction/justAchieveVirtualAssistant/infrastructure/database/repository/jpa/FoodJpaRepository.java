package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa;

import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FoodEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodJpaRepository extends JpaRepository<FoodEntity, Integer> {
    List<FoodEntity> findAllByUserId(UserEntity userId);
}
