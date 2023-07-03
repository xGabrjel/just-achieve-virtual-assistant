package com.appliaction.justAchieveVirtualAssistant.domain;

import com.appliaction.justAchieveVirtualAssistant.security.support.TokenExpirationTime;
import lombok.*;

import java.util.Date;

@With
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "token", "expirationTime"})
public class PasswordResetToken {


    private Long id;
    private String token;
    private Date expirationTime;
    private User user;

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expirationTime = TokenExpirationTime.getExpirationTime();
    }
}
