package com.appliaction.justAchieveVirtualAssistant.domain;

import com.appliaction.justAchieveVirtualAssistant.security.RoleEntity;
import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "userId")
@ToString(of = {"userId", "username", "email", "active", "roles"})
public class User {

    Integer userId;
    String username;
    String email;
    String password;
    Boolean active;
    Set<RoleEntity> roles;
}
