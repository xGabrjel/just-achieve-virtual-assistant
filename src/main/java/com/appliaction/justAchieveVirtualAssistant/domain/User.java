package com.appliaction.justAchieveVirtualAssistant.domain;

import lombok.*;

import java.util.Collection;

@With
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
@ToString(of = {"userId", "username", "email", "active", "roles"})
public class User {

    private Integer userId;
    private String username;
    private String email;
    private String password;
    private Boolean active = false;
    private Collection<Role> roles;

    public User(String username, String email, String password, Collection<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
