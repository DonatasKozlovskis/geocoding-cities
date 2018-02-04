package com.doko.sandbox.rest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class GeocodeRepositoryTest {
    private static final GeocodeGeometryDto geocodeGeometryDto = new GeocodeGeometryDto();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    @Qualifier("geocodeRepositoryWithResult")
    private GeocodeRepository geocodeRepositoryWithResult;

    @Autowired
    @Qualifier("geocodeRepositoryWithException")
    private GeocodeRepository geocodeRepositoryWithException;

    @Test
    public void getGeometry_retriesEmployed_resultObtained() {
        GeocodeGeometryDto result = geocodeRepositoryWithResult.getGeometry("Test");

        verify(geocodeRepositoryWithResult, times(3)).getGeometry(anyString());
        assertEquals(geocodeGeometryDto, result);
    }

    @Test
    public void getGeometry_retryThreeTimes_exceptionThrown() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Exception 3");
        GeocodeGeometryDto result = geocodeRepositoryWithException.getGeometry("Test");
    }

    @Configuration
    @EnableRetry
    public static class SpringConfig {

        @Bean("geocodeRepositoryWithResult")
        public GeocodeRepository geocodeRepositoryWithResult() {

            GeocodeRepository geocodeRepository = mock(GeocodeRepository.class);
            when(geocodeRepository.getGeometry(any()))
                    .thenThrow(new RuntimeException("Remote Exception 1"))
                    .thenThrow(new RuntimeException("Remote Exception 2"))
                    .thenReturn(geocodeGeometryDto);
            return geocodeRepository;
        }

        @Bean("geocodeRepositoryWithException")
        public GeocodeRepository geocodeRepositoryWithException() {

            GeocodeRepository geocodeRepository = mock(GeocodeRepository.class);
            when(geocodeRepository.getGeometry(any()))
                    .thenThrow(new RuntimeException("Exception 1"))
                    .thenThrow(new RuntimeException("Exception 2"))
                    .thenThrow(new IllegalStateException("Exception 3"))
                    .thenThrow(new RuntimeException("Exception 4"));
            return geocodeRepository;
        }
    }
}