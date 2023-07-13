package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FoodEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
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
                .userId(EntityFixtures.someUserEntity())
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
        assertEquals(entity.getFoodId(), domain.getFoodId());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getCalories(), domain.getCalories());
        assertEquals(entity.getServingSizeG(), domain.getServingSizeG());
        assertEquals(entity.getFatTotalG(), domain.getFatTotalG());
        assertEquals(entity.getFatSaturatedG(), domain.getFatSaturatedG());
        assertEquals(entity.getProteinG(), domain.getProteinG());
        assertEquals(entity.getSodiumMg(), domain.getSodiumMg());
        assertEquals(entity.getPotassiumMg(), domain.getPotassiumMg());
        assertEquals(entity.getCholesterolMg(), domain.getCholesterolMg());
        assertEquals(entity.getCarbohydratesTotalG(), domain.getCarbohydratesTotalG());
        assertEquals(entity.getFiberG(), domain.getFiberG());
        assertEquals(entity.getSugarG(), domain.getSugarG());
        assertEquals(User.class, domain.getUserId().getClass());
        assertEquals(entity.getUserId().getUserId(), domain.getUserId().getUserId());
        assertEquals(entity.getUserId().getUsername(), domain.getUserId().getUsername());
        assertEquals(entity.getUserId().getEmail(), domain.getUserId().getEmail());
    }

    @Test
    void mapFromEntityWorksCorrectly() {
        //given
        Food domain = Food.builder()
                .foodId(1)
                .userId(DomainFixtures.someUser())
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
        assertEquals(domain.getFoodId(), entity.getFoodId());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getCalories(), entity.getCalories());
        assertEquals(domain.getServingSizeG(), entity.getServingSizeG());
        assertEquals(domain.getFatTotalG(), entity.getFatTotalG());
        assertEquals(domain.getFatSaturatedG(), entity.getFatSaturatedG());
        assertEquals(domain.getProteinG(), entity.getProteinG());
        assertEquals(domain.getSodiumMg(), entity.getSodiumMg());
        assertEquals(domain.getPotassiumMg(), entity.getPotassiumMg());
        assertEquals(domain.getCholesterolMg(), entity.getCholesterolMg());
        assertEquals(domain.getCarbohydratesTotalG(), entity.getCarbohydratesTotalG());
        assertEquals(domain.getFiberG(), entity.getFiberG());
        assertEquals(domain.getSugarG(), entity.getSugarG());
        assertEquals(UserEntity.class, entity.getUserId().getClass());
        assertEquals(domain.getUserId().getUserId(), entity.getUserId().getUserId());
        assertEquals(domain.getUserId().getUsername(), entity.getUserId().getUsername());
        assertEquals(domain.getUserId().getEmail(), entity.getUserId().getEmail());
    }
}