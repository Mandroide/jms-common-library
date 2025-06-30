package com.transunion.rise.webservices.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)  // a fallback
@PropertySource("classpath:common/application.properties")  // a fallback
@PropertySource("classpath:${spring.profiles.active}.properties")
public class PropertyConfiguration {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
