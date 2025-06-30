package com.transunion.rise.webservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transunion.rise.webservices.configuration.*;
import com.transunion.rise.webservices.model.JwtToken;
import com.transunion.rise.webservices.pattern.command.CommandBus;
import com.transunion.rise.webservices.service.TUApiService;
import com.transunion.rise.webservices.settings.Settings;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@Slf4j
@SpringJUnitConfig(classes = {
        TUApiService.class,
        Settings.class,
        CommandBus.class,
        CQRSConfiguration.class,
        JmsGeneralConfiguration.class,
        RestApiConfiguration.class,
        PropertyConfiguration.class,
        ObjectMapperConfiguration.class
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("localtest")
class TUApiServiceIntTest {
    @Autowired
    private TUApiService tuApiService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${apitu.path.base-url}")
    private String baseUrlPath;
    @Value("${apitu.path.auth.login}")
    private String authLoginPath;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void login() throws JsonProcessingException {
        JwtToken jwtToken = JwtToken.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .tokenType("Bearer")
                .expiresIn(3000L)
                .build();
        mockServer.expect(requestTo(baseUrlPath + authLoginPath))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(objectMapper.writeValueAsString(jwtToken), MediaType.APPLICATION_JSON));
        String accessToken = tuApiService.login("F0000", "username", "password");
        log.info("accessToken: {}", accessToken);
        Assertions.assertNotNull(accessToken);
    }
}
