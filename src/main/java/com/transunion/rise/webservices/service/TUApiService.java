package com.transunion.rise.webservices.service;

import com.transunion.rise.webservices.LoginOauthRequest;
import com.transunion.rise.webservices.exceptions.TUAuthException;
import com.transunion.rise.webservices.command.GetTokenCommand;
import com.transunion.rise.webservices.model.JwtToken;
import com.transunion.rise.webservices.pattern.command.CommandBus;
import com.transunion.rise.webservices.settings.Settings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
//@RequiredArgsConstructor
public class TUApiService {
    private static final String APPLICATION = "TUX";
    private final Settings settings;
    private final CommandBus commandBus;

    private final RestTemplate restTemplate;
    @Value("${apitu.path.base-url}")
    private final String baseUrl;
    @Value("${apitu.path.auth.login}")
    private final String relativePath;

    public TUApiService(
            Settings settings,
            CommandBus commandBus,
            RestTemplate restTemplate,
            @Value("${apitu.path.base-url}") String baseUrl,
            @Value("${apitu.path.auth.login}") String relativePath
    ) {
        this.settings = settings;
        this.commandBus = commandBus;
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.relativePath = relativePath;
    }

    public String handle(GetTokenCommand command) {
        LoginOauthRequest request = command.getRequest();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<LoginOauthRequest> httpEntity = new HttpEntity<>(request, headers);
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl + relativePath, httpEntity, String.class);
//
//        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
//            throw new TUAuthException("Authentication failed");
//        }
//
//        return responseEntity.getBody();
        return "TOKEN";
    }

    public String login(String subscriber, String username, String password) {
        LoginOauthRequest loginOauthRequest = LoginOauthRequest.builder()
                .application(APPLICATION)
                .subscriber(subscriber)
                .username(username)
                .password(password)
                .build();
        return handle(GetTokenCommand.builder().request(loginOauthRequest).build());
//        return commandBus.sendCommand(GetTokenCommand.builder().request(loginOauthRequest).build());
    }
}
