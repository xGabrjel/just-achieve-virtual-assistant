package com.appliaction.justAchieveVirtualAssistant.domain;

import com.appliaction.justAchieveVirtualAssistant.security.support.TokenExpirationTime;
import lombok.*;

import java.util.Date;

@With
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "expirationTime", "token"})
public class VerificationToken {


    private Long id;
    private Date expirationTime;
    private String token;
    private User user;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expirationTime = TokenExpirationTime.getExpirationTime();
    }
}
