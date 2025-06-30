package com.transunion.rise.webservices.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.MessageChannel;

@Slf4j
@Configuration
@EnableIntegration
public class CQRSConfiguration {
    @Bean
    public MessagingTemplate messagingTemplate(MessageChannel commandChannel) {
        log.info("Configure CQRS message template");
        MessagingTemplate template = new MessagingTemplate(commandChannel);
        template.setReceiveTimeout(5000);
        return template;
    }

    @Bean
    public MessageChannel commandChannel() {
        log.info("Configure CQRS Command channel");
        return new PublishSubscribeChannel();
    }
}