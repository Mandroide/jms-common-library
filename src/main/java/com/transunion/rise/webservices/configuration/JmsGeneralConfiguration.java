package com.transunion.rise.webservices.configuration;

import com.transunion.rise.webservices.converters.ServiceMessageConverter;
import com.transunion.rise.webservices.modules.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;

import java.util.Arrays;


@Slf4j
@Configuration(proxyBeanMethods = false)
public class JmsGeneralConfiguration {
    private final String brokerUrl;
    private final String destinationName;
    private final String concurrency;
    private final int sessionCacheSize;
    private final long receiveTimeout;
    private final String[] trustedPackages;

    public JmsGeneralConfiguration(@Value("${jms.connection.broker-url}") String brokerUrl,
                                   @Value("${jms.listener.destination-name}") String destinationName,
                                   @Value("${jms.listener.concurrency}") String concurrency,
                                   @Value("${jms.connection.session-cache-size}") int sessionCacheSize,
                                   @Value("${jms.jms-template.receive-timeout}") long receiveTimeout,
                                   @Value("${jms.connection.activemq.trusted-packages}") String[] trustedPackages) {
        this.brokerUrl = brokerUrl;
        this.destinationName = destinationName;
        this.concurrency = concurrency;
        this.sessionCacheSize = sessionCacheSize;
        this.receiveTimeout = receiveTimeout;
        this.trustedPackages = trustedPackages;
    }

    public DefaultMessageListenerContainer createMessageListenerContainer(AbstractService abstractService, String messageSelector) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        CachingConnectionFactory cf = cachingConnectionFactory();
        container.setConnectionFactory(cf);
        container.setMessageConverter(messageConverter());
        container.setConcurrency(concurrency);
        container.setDestinationName(destinationName);
        container.setMessageSelector(messageSelector);
        container.setMessageListener(abstractService);
        return container;
    }

    @Bean
    JmsTemplate jmsTemplateForService() {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory());
        jmsTemplate.setMessageConverter(messageConverter());
        jmsTemplate.setReceiveTimeout(receiveTimeout);
        log.info("Created JMS template for service");
        return jmsTemplate;
    }

    private MessageConverter messageConverter() {
        return new ServiceMessageConverter();
    }

    private CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory ccf = new CachingConnectionFactory(amqFactory());
        ccf.setSessionCacheSize(sessionCacheSize);
        return ccf;
    }

    private ActiveMQConnectionFactory amqFactory() {
        ActiveMQConnectionFactory amqFactory = new ActiveMQConnectionFactory(brokerUrl);
        amqFactory.setTrustedPackages(Arrays.asList(trustedPackages));
        return amqFactory;
    }

}
