package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.Role;
import com.appliaction.justAchieveVirtualAssistant.security.user.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleEntityMapper {

    Role mapFromEntity(RoleEntity entity);

    RoleEntity mapToEntity(Role entity);
}
