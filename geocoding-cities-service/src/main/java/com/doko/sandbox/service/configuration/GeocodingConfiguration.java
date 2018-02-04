package com.doko.sandbox.service.configuration;


import com.doko.sandbox.input.InputService;
import com.doko.sandbox.rest.GeocodeRepository;
import com.doko.sandbox.service.GeocodingService;
import com.doko.sandbox.service.GeometryStoreMapper;
import com.doko.sandbox.store.GeometryStoreService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.doko.sandbox")
@EnableConfigurationProperties({GeocodingProperties.class})
public class GeocodingConfiguration {
    @Bean
    public GeocodingService geocitiesService(GeocodingProperties geocodingProperties,
                                             InputService inputService,
                                             GeocodeRepository geocodeRepository,
                                             GeometryStoreService geometryStoreService) {
        return new GeocodingService(
                geocodingProperties,
                inputService,
                geocodeRepository,
                geometryStoreService,
                new GeometryStoreMapper());
    }
}
