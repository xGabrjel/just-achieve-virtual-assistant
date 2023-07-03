package com.appliaction.justAchieveVirtualAssistant.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findAll();

    @Modifying
    @Query(value = "UPDATE UserEntity u SET u.username =:username, u.email =:email WHERE u.userId=:userId")
    void update(@Param("username") String username, @Param("email") String email, @Param("userId") int userId);
}
