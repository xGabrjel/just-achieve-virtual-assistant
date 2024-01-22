package com.appliaction.justAchieveVirtualAssistant.security.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenJpaRepository extends JpaRepository<VerificationTokenEntity, Long> {

    Optional<VerificationTokenEntity> findByToken(String token);

    void deleteByUserUserId(int userId);
}
