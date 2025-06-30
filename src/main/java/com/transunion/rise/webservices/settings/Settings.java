package com.transunion.rise.webservices.settings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Settings {
    private final String apiBaseUrl;

    public Settings(@Value("${apitu.path.base-url}") String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }
}
