package com.doko.sandbox.rest.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@ConfigurationProperties(prefix = "google")
public class GoogleProperties {
    @NotNull
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "GoogleProperties{" +
                "apiKey='" + apiKey + '\'' +
                '}';
    }
}
