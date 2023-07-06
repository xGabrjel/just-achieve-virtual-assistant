package com.appliaction.justAchieveVirtualAssistant.security.support;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TokenExpirationTimeTest {

    @Test
    void getExpirationTimeWorksCorrectly() {
        //given
        int expirationTimeMinutes = 10;
        Calendar calendar = mock(Calendar.class);
        Date currentDate = new Date();
        Date expectedExpirationTime = new Date(currentDate.getTime() + expirationTimeMinutes * 60 * 1000);

        when(calendar.getTime()).thenReturn(currentDate);
        when(calendar.getTimeInMillis()).thenReturn(currentDate.getTime());
        when(calendar.getTimeInMillis() + Calendar.MINUTE * expirationTimeMinutes).thenReturn(expectedExpirationTime.getTime());

        //when
        Date expirationTime = TokenExpirationTime.getExpirationTime();

        //then
        assertThat(expirationTime.getTime() / 10000).isEqualTo(expectedExpirationTime.getTime() / 10000);
    }
}