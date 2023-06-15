package com.appliaction.justAchieveVirtualAssistant.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique = true, nullable = false)
    private Integer roleId;

    @Column(name = "role", nullable = false)
    private String role;
}
