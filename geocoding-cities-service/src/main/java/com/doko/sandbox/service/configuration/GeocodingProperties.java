package com.doko.sandbox.service.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@ConfigurationProperties(prefix = "geocoding")
public class GeocodingProperties {
    @NotNull
    private String inputSource;

    @NotNull
    private boolean refreshStore = false;

    public boolean isRefreshStore() {
        return refreshStore;
    }

    public void setRefreshStore(boolean refreshStore) {
        this.refreshStore = refreshStore;
    }

    public String getInputSource() {
        return inputSource;
    }

    public void setInputSource(String inputSource) {
        this.inputSource = inputSource;
    }

    @Override
    public String toString() {
        return "GeocodingProperties{" +
                "inputSource='" + inputSource + '\'' +
                ", refreshStore=" + refreshStore +
                '}';
    }
}
