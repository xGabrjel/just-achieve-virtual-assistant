package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa;

import com.appliaction.justAchieveVirtualAssistant.configuration.PersistenceContainerTestConfiguration;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class UserProfileJpaRepositoryTest {

    private UserProfileJpaRepository userProfileJpaRepository;

    @Test
    void customUpdateWeightByProfileIdWorksCorrectly() {
        // given
        Integer adminProfileId = 1;
        BigDecimal newWeight = BigDecimal.valueOf(100.5);

        // when
        userProfileJpaRepository.updateWeightByProfileId(adminProfileId, newWeight);

        // then
        UserProfileEntity updatedUserProfileEntity = userProfileJpaRepository.findById(adminProfileId).orElseThrow();
        assertEquals(newWeight, updatedUserProfileEntity.getWeight());
    }
}