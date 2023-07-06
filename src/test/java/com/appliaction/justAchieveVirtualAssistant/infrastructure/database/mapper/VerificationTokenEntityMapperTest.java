package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.Role;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.domain.VerificationToken;
import com.appliaction.justAchieveVirtualAssistant.security.registration.token.VerificationTokenEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.RoleEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class VerificationTokenEntityMapperTest {

    private final VerificationTokenEntityMapper verificationTokenEntityMapper = Mappers.getMapper(VerificationTokenEntityMapper.class);

    @Test
    void verificationTokenMapFromEntityMapperWorksCorrectly() {
        //given
        VerificationTokenEntity entity = new VerificationTokenEntity();
        entity.setId(1L);
        entity.setToken(UUID.randomUUID().toString());
        entity.setExpirationTime(Date.from(Instant.now()));
        entity.setUser(EntityFixtures.someUserEntity());

        //when
        VerificationToken domain = verificationTokenEntityMapper.mapFromEntity(entity);
        VerificationToken nullMapping = verificationTokenEntityMapper.mapFromEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getToken(), domain.getToken());
        assertEquals(entity.getExpirationTime(), domain.getExpirationTime());
        assertEquals(User.class, domain.getUser().getClass());
    }

    @Test
    void verificationTokenMapToEntityMapperWorksCorrectly() {
        //given
        VerificationToken domain = new VerificationToken();
        domain.setId(1L);
        domain.setToken(UUID.randomUUID().toString());
        domain.setExpirationTime(Date.from(Instant.now()));
        domain.setUser(DomainFixtures.someUser());

        //when
        VerificationTokenEntity entity = verificationTokenEntityMapper.mapToEntity(domain);
        VerificationTokenEntity nullMapping = verificationTokenEntityMapper.mapToEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getToken(), entity.getToken());
        assertEquals(domain.getExpirationTime(), entity.getExpirationTime());
        assertEquals(UserEntity.class, entity.getUser().getClass());
    }

    @Test
    void verificationTokenMapToEntityMapperWithNullUserWorksCorrectly() {
        //given
        VerificationToken domain = new VerificationToken();
        domain.setId(1L);
        domain.setToken(UUID.randomUUID().toString());
        domain.setExpirationTime(Date.from(Instant.now()));
        domain.setUser(null);

        //when
        VerificationTokenEntity entity = verificationTokenEntityMapper.mapToEntity(domain);

        //then
        assertNull(entity.getUser());
    }

    @Test
    void verificationTokenMapFromEntityMapperWithNullUserWorksCorrectly() {
        //given
        VerificationTokenEntity entity = new VerificationTokenEntity();
        entity.setId(1L);
        entity.setToken(UUID.randomUUID().toString());
        entity.setExpirationTime(Date.from(Instant.now()));
        entity.setUser(null);

        //when
        VerificationToken domain = verificationTokenEntityMapper.mapFromEntity(entity);

        //then
        assertNull(domain.getUser());
    }

    @Test
    void verificationTokenMapFromEntityMapperWithEmptyRolesCollectionWorksCorrectly() {
        //given
        UserEntity userEntity = EntityFixtures.someUserEntity();
        userEntity.setRoles(Collections.emptyList());

        VerificationTokenEntity entity = new VerificationTokenEntity();
        entity.setId(1L);
        entity.setToken(UUID.randomUUID().toString());
        entity.setExpirationTime(Date.from(Instant.now()));
        entity.setUser(userEntity);

        //when
        VerificationToken domain = verificationTokenEntityMapper.mapFromEntity(entity);

        //then
        assertNotNull(domain);
        assertEquals(0, domain.getUser().getRoles().size());
    }

    @Test
    void verificationTokenMapToEntityMapperWithEmptyRolesCollectionWorksCorrectly() {
        //given
        User user = DomainFixtures.someUser();
        user.setRoles(Collections.emptyList());

        VerificationToken domain = new VerificationToken();
        domain.setId(1L);
        domain.setToken(UUID.randomUUID().toString());
        domain.setExpirationTime(Date.from(Instant.now()));
        domain.setUser(user);

        //when
        VerificationTokenEntity entity = verificationTokenEntityMapper.mapToEntity(domain);

        //then
        assertNotNull(entity);
        assertEquals(0, entity.getUser().getRoles().size());
    }

    @Test
    void verificationTokenMapFromEntityMapperWithRolesCollectionWorksCorrectly() {
        //given
        Collection<RoleEntity> roleEntities = new ArrayList<>();
        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setRoleId(1);
        roleEntity1.setRole("ROLE_ADMIN");
        roleEntities.add(roleEntity1);

        RoleEntity roleEntity2 = new RoleEntity();
        roleEntity2.setRoleId(2);
        roleEntity2.setRole("ROLE_USER");
        roleEntities.add(roleEntity2);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1);
        userEntity.setUsername("test.test");
        userEntity.setEmail("test.test@test.com");
        userEntity.setPassword("password123");
        userEntity.setActive(true);
        userEntity.setRoles(roleEntities);

        VerificationTokenEntity entity = new VerificationTokenEntity();
        entity.setId(1L);
        entity.setToken(UUID.randomUUID().toString());
        entity.setExpirationTime(Date.from(Instant.now()));
        entity.setUser(userEntity);

        //when
        VerificationToken domain = verificationTokenEntityMapper.mapFromEntity(entity);

        //then
        assertNotNull(domain);
        assertEquals(roleEntities.size(), domain.getUser().getRoles().size());
    }

    @Test
    void verificationTokenMapToEntityMapperWithRolesCollectionWorksCorrectly() {
        //given
        Collection<Role> roles = new ArrayList<>();
        Role role1 = new Role();
        role1.setRoleId(1);
        role1.setRole("USER");
        roles.add(role1);

        Role role2 = new Role();
        role2.setRoleId(2);
        role2.setRole("ADMIN");
        roles.add(role2);

        User user = new User();
        user.setUserId(1);
        user.setUsername("test.test");
        user.setEmail("test.test@test.com");
        user.setPassword("password123");
        user.setActive(true);
        user.setRoles(roles);

        VerificationToken domain = new VerificationToken();
        domain.setId(1L);
        domain.setToken(UUID.randomUUID().toString());
        domain.setExpirationTime(Date.from(Instant.now()));
        domain.setUser(user);

        //when
        VerificationTokenEntity entity = verificationTokenEntityMapper.mapToEntity(domain);

        //then
        assertNotNull(entity);
        assertEquals(roles.size(), entity.getUser().getRoles().size());
    }
}