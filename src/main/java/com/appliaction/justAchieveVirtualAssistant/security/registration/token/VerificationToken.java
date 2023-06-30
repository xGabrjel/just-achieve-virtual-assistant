package com.appliaction.justAchieveVirtualAssistant.security.registration.token;

import com.appliaction.justAchieveVirtualAssistant.security.support.TokenExpirationTime;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "verification_token")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Column(name = "expiration_time")
    private Date expirationTime;

    @Column(name = "token")
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public VerificationToken(String token, UserEntity user) {
        this.token = token;
        this.user = user;
        this.expirationTime = TokenExpirationTime.getExpirationTime();
    }
}
