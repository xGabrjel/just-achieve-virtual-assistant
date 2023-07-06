package com.appliaction.justAchieveVirtualAssistant.security.support;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlUtilTest {

    @InjectMocks
    private UrlUtil urlUtil;
    @Mock
    private HttpServletRequest request;
    @Test
    void getApplicationUrlWorksCorrectly() {
        //given
        String requestUrl = "http://localhost:8080";
        String servletPath = "/servlet";
        String expectedBaseUrl = "http://localhost:8080";
        when(request.getRequestURL()).thenReturn(new StringBuffer(requestUrl));
        when(request.getServletPath()).thenReturn(servletPath);

        //when
        String result = UrlUtil.getApplicationUrl(request);

        //then
        assertEquals(expectedBaseUrl, result);
    }
}