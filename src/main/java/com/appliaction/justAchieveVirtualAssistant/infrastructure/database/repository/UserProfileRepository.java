package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository;

import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Integer> {
    Set<UserProfileEntity> findByPhone(final @Param("phone") String phoneNumber);
}
