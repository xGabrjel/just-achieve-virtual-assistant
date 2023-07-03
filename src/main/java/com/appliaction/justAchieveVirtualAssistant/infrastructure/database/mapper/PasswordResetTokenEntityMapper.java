package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.PasswordResetToken;
import com.appliaction.justAchieveVirtualAssistant.security.registration.password.PasswordResetTokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PasswordResetTokenEntityMapper {

    PasswordResetToken mapFromEntity(PasswordResetTokenEntity entity);

    PasswordResetTokenEntity mapToEntity(PasswordResetToken domain);
}
