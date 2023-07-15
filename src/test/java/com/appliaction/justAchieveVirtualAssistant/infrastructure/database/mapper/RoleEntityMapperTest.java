package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.Role;
import com.appliaction.justAchieveVirtualAssistant.security.user.RoleEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RoleEntityMapperTest {

    private final RoleEntityMapper roleEntityMapper = Mappers.getMapper(RoleEntityMapper.class);

    @Test
    void roleMapFromEntityMapperWorksCorrectly() {
        //given
        RoleEntity entity = new RoleEntity();
        entity.setRoleId(1);
        entity.setRole("USER");

        //when
        Role domain = roleEntityMapper.mapFromEntity(entity);
        Role nullMapping = roleEntityMapper.mapFromEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(entity.getRole(), domain.getRole());
        assertEquals(entity.getRoleId(), domain.getRoleId());
    }
    @Test
    void roleMapToEntityMapperWorksCorrectly() {
        //given
        Role domain = new Role();
        domain.setRoleId(1);
        domain.setRole("USER");


        //when
        RoleEntity entity = roleEntityMapper.mapToEntity(domain);
        RoleEntity nullMapping = roleEntityMapper.mapToEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(domain.getRole(), entity.getRole());
        assertEquals(domain.getRoleId(), entity.getRoleId());
    }
}