package com.appliaction.justAchieveVirtualAssistant.domain;

import lombok.*;

@With
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "roleId")
@ToString(of = {"roleId", "role"})
public class Role {

    private int roleId;
    private String role;

    public Role(String role) {
        this.role = role;
    }
}
