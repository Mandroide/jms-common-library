package com.transunion.rise.webservices.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;


//@Configuration
@Getter
@Setter
public class JmsProperties {
    private String brokerUrl;
    private String listenerDestinationName;
    private String concurrency;
    private int sessionCacheSize;
    private long receiveTimeout;
    private String[] trustedPackages;
}
