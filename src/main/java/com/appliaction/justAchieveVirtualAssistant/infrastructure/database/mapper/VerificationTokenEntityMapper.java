package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.VerificationToken;
import com.appliaction.justAchieveVirtualAssistant.security.registration.token.VerificationTokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VerificationTokenEntityMapper {

    VerificationToken mapFromEntity(VerificationTokenEntity entity);

    VerificationTokenEntity mapToEntity(VerificationToken domain);
}
