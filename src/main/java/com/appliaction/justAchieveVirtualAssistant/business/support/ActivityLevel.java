package com.appliaction.justAchieveVirtualAssistant.business.support;

import java.math.BigDecimal;

public enum ActivityLevel {
    SEDENTARY(BigDecimal.valueOf(1.2)),
    LIGHTLY_ACTIVE(BigDecimal.valueOf(1.375)),
    MODERATELY_ACTIVE(BigDecimal.valueOf(1.55)),
    ACTIVE(BigDecimal.valueOf(1.725)),
    VERY_ACTIVE(BigDecimal.valueOf(1.9));

    private final BigDecimal armMultiplier;

    ActivityLevel(BigDecimal armMultiplier) {
        this.armMultiplier = armMultiplier;
    }

    public BigDecimal getArmMultiplier() {
        return armMultiplier;
    }
}
