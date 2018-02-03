package com.doko.sandbox.rest;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public interface GeocodeRepository {
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    GeocodeGeometryDto getGeometry(String address);
}
