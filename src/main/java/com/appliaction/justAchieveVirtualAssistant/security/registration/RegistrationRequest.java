package com.appliaction.justAchieveVirtualAssistant.security.registration;

import com.appliaction.justAchieveVirtualAssistant.security.user.RoleEntity;
import lombok.Data;

import java.util.List;

@Data
public class RegistrationRequest {

    private String username;
    private String email;
    private String password;
    private List<RoleEntity> roles;
}
