package com.doko.sandbox.rest;

import com.doko.google.rest.model.GeocodeResponse;
import com.doko.google.rest.model.Result;
import com.doko.sandbox.rest.configuration.GoogleProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class GoogleGeocodeRepository implements GeocodeRepository {
    private static final Logger logger = LoggerFactory.getLogger(GoogleGeocodeRepository.class);

    private static final String URL = "https://maps.googleapis.com/maps/api/geocode/xml?key={key}&address={address}";
    private final GoogleProperties googleProperties;
    private final RestTemplate restTemplate;
    private final GeometryMapper mapper;

    public GoogleGeocodeRepository(GoogleProperties googleProperties, RestTemplate restTemplate, GeometryMapper mapper) {
        this.googleProperties = googleProperties;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    @Override
    public GeocodeGeometryDto getGeometry(String address) {

        logger.info("Getting geometry for {}", address);

        Optional<GeocodeResponse> response = Optional.of(restTemplate
                .getForObject(URL, GeocodeResponse.class, googleProperties.getApiKey(), address));
        return response.map(GeocodeResponse::getResult)
                .map(Result::getGeometry)
                .map(mapper::assembleGeometryDto)
                .orElse(null);
    }
}
