package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FoodEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FoodEntityMapperTest {

    private final FoodEntityMapper foodEntityMapper = Mappers.getMapper(FoodEntityMapper.class);

    @Test
    void mapToEntityWorksCorrectly() {
        //given
        FoodEntity entity = FoodEntity.builder()
                .foodId(1)
                .profileId(EntityFixtures.someUserProfileEntity())
                .name("Apple")
                .calories(new BigDecimal("52"))
                .servingSizeG(100)
                .fatTotalG(new BigDecimal("0.2"))
                .fatSaturatedG(new BigDecimal("0"))
                .proteinG(new BigDecimal("0.3"))
                .sodiumMg(new BigDecimal("1"))
                .potassiumMg(new BigDecimal("107"))
                .cholesterolMg(new BigDecimal("0"))
                .carbohydratesTotalG(new BigDecimal("14"))
                .fiberG(new BigDecimal("2.4"))
                .sugarG(new BigDecimal("10.3"))
                .build();

        //when
        Food domain = foodEntityMapper.mapFromEntity(entity);
        Food nullMapping = foodEntityMapper.mapFromEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getFoodId(), domain.getFoodId());
        assertEquals(entity.getSugarG(), domain.getSugarG());
        assertEquals(entity.getFiberG(), domain.getFiberG());
        assertEquals(entity.getCalories(), domain.getCalories());
        assertEquals(entity.getProteinG(), domain.getProteinG());
        assertEquals(entity.getSodiumMg(), domain.getSodiumMg());
        assertEquals(entity.getFatTotalG(), domain.getFatTotalG());
        assertEquals(entity.getPotassiumMg(), domain.getPotassiumMg());
        assertEquals(entity.getServingSizeG(), domain.getServingSizeG());
        assertEquals(UserProfile.class, domain.getProfileId().getClass());
        assertEquals(entity.getFatSaturatedG(), domain.getFatSaturatedG());
        assertEquals(entity.getCholesterolMg(), domain.getCholesterolMg());
        assertEquals(entity.getProfileId().getAge(), domain.getProfileId().getAge());
        assertEquals(entity.getProfileId().getSex(), domain.getProfileId().getSex());
        assertEquals(entity.getCarbohydratesTotalG(), domain.getCarbohydratesTotalG());
        assertEquals(entity.getProfileId().getName(), domain.getProfileId().getName());
        assertEquals(entity.getProfileId().getPhone(), domain.getProfileId().getPhone());
        assertEquals(entity.getProfileId().getWeight(), domain.getProfileId().getWeight());
        assertEquals(entity.getProfileId().getHeight(), domain.getProfileId().getHeight());
        assertEquals(entity.getProfileId().getSurname(), domain.getProfileId().getSurname());
        assertEquals(entity.getProfileId().getProfileId(), domain.getProfileId().getProfileId());
    }

    @Test
    void mapFromEntityWorksCorrectly() {
        //given
        Food domain = Food.builder()
                .foodId(1)
                .profileId(DomainFixtures.someUserProfile())
                .name("Apple")
                .calories(new BigDecimal("52"))
                .servingSizeG(100)
                .fatTotalG(new BigDecimal("0.2"))
                .fatSaturatedG(new BigDecimal("0"))
                .proteinG(new BigDecimal("0.3"))
                .sodiumMg(new BigDecimal("1"))
                .potassiumMg(new BigDecimal("107"))
                .cholesterolMg(new BigDecimal("0"))
                .carbohydratesTotalG(new BigDecimal("14"))
                .fiberG(new BigDecimal("2.4"))
                .sugarG(new BigDecimal("10.3"))
                .build();

        //when
        FoodEntity entity = foodEntityMapper.mapToEntity(domain);
        FoodEntity nullMapping = foodEntityMapper.mapToEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getSugarG(), entity.getSugarG());
        assertEquals(domain.getFiberG(), entity.getFiberG());
        assertEquals(domain.getFoodId(), entity.getFoodId());
        assertEquals(domain.getCalories(), entity.getCalories());
        assertEquals(domain.getProteinG(), entity.getProteinG());
        assertEquals(domain.getSodiumMg(), entity.getSodiumMg());
        assertEquals(domain.getFatTotalG(), entity.getFatTotalG());
        assertEquals(domain.getPotassiumMg(), entity.getPotassiumMg());
        assertEquals(domain.getServingSizeG(), entity.getServingSizeG());
        assertEquals(domain.getFatSaturatedG(), entity.getFatSaturatedG());
        assertEquals(domain.getCholesterolMg(), entity.getCholesterolMg());
        assertEquals(UserProfileEntity.class, entity.getProfileId().getClass());
        assertEquals(domain.getProfileId().getSex(), entity.getProfileId().getSex());
        assertEquals(domain.getProfileId().getAge(), entity.getProfileId().getAge());
        assertEquals(domain.getCarbohydratesTotalG(), entity.getCarbohydratesTotalG());
        assertEquals(domain.getProfileId().getName(), entity.getProfileId().getName());
        assertEquals(domain.getProfileId().getPhone(), entity.getProfileId().getPhone());
        assertEquals(domain.getProfileId().getWeight(), entity.getProfileId().getWeight());
        assertEquals(domain.getProfileId().getHeight(), entity.getProfileId().getHeight());
        assertEquals(domain.getProfileId().getSurname(), entity.getProfileId().getSurname());
        assertEquals(domain.getProfileId().getProfileId(), entity.getProfileId().getProfileId());
    }
}