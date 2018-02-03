package com.doko.sandbox.store.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@ConfigurationProperties(prefix = "datastore")
public class DatastoreProperties {
    @NotNull
    private int closestLimit;

    public int getClosestLimit() {
        return closestLimit;
    }

    public void setClosestLimit(int closestLimit) {
        this.closestLimit = closestLimit;
    }


    @Override
    public String toString() {
        return "DatastoreProperties{" +
                "closestLimit=" + closestLimit +
                '}';
    }
}
