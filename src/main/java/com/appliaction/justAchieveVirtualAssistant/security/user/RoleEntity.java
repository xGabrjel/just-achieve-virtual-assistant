package com.appliaction.justAchieveVirtualAssistant.security.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "app_role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique = true, nullable = false)
    private int roleId;

    @Column(name = "role", nullable = false)
    private String role;

    public RoleEntity(String role) {
        this.role = role;
    }
}
