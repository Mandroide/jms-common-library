package com.transunion.rise.webservices.command.handler;

import com.transunion.rise.webservices.LoginOauthRequest;
import com.transunion.rise.webservices.exceptions.TUAuthException;
import com.transunion.rise.webservices.command.GetTokenCommand;
import com.transunion.rise.webservices.pattern.command.CommandEvent;
import com.transunion.rise.webservices.pattern.command.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.ExecutionException;


@Slf4j
@CommandEvent(command = GetTokenCommand.class)
@Component
public class GetTokenCommandHandler implements CommandHandler<String, GetTokenCommand> {
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String relativePath;

    public GetTokenCommandHandler(RestTemplate restTemplate,
                                  @Value("${apitu.path.base-url}") String baseUrl,
                                  @Value("${apitu.path.auth.login}") String relativePath) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.relativePath = relativePath;
    }

    @Override
    public String handle(GetTokenCommand command) throws ExecutionException {
        LoginOauthRequest request = command.getRequest();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info("Opening {}", baseUrl + relativePath);
        HttpEntity<LoginOauthRequest> httpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl + relativePath, httpEntity, String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new TUAuthException("Authentication failed");
        }

        return "Tokenized";
        // TODO 2025-06-29 Uncomment
//        return responseEntity.getBody();
    }
}
