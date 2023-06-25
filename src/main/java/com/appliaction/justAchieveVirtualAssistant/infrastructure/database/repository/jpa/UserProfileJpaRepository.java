package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa;

import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UserProfileJpaRepository extends JpaRepository<UserProfileEntity, Integer> {
    Optional<UserProfileEntity> findByUserUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE UserProfileEntity user SET user.weight = :newWeight WHERE user.profileId = :profileId")
    void updateWeightByProfileId(Integer profileId, BigDecimal newWeight);
}
