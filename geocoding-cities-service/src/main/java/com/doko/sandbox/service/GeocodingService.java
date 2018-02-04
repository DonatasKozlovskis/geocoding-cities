package com.doko.sandbox.service;

import com.doko.sandbox.input.InputService;
import com.doko.sandbox.rest.GeocodeGeometryDto;
import com.doko.sandbox.rest.GeocodeRepository;
import com.doko.sandbox.service.configuration.GeocodingProperties;
import com.doko.sandbox.store.Geometry;
import com.doko.sandbox.store.GeometryStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GeocodingService {
    private static final Logger logger = LoggerFactory.getLogger(GeocodingService.class);
    private final GeocodingProperties geocodingProperties;
    private final InputService inputService;
    private final GeocodeRepository geocodeRepository;
    private final GeometryStoreService geometryStoreService;
    private final GeometryStoreMapper geometryStoreMapper;

    public GeocodingService(GeocodingProperties geocodingProperties,
                            InputService inputService,
                            GeocodeRepository geocodeRepository,
                            GeometryStoreService geometryStoreService,
                            GeometryStoreMapper geometryStoreMapper) {
        this.geocodingProperties = geocodingProperties;
        this.inputService = inputService;
        this.geocodeRepository = geocodeRepository;
        this.geometryStoreService = geometryStoreService;
        this.geometryStoreMapper = geometryStoreMapper;
    }

    public List<Geometry> getClosestLocations(Double lat, Double lng) {
        List<Geometry> geometries = geometryStoreService.findByLocation(lat, lng);
        if (geometries.isEmpty()) {
            geometries = geometryStoreService.findClosest(lat, lng);
        }
        return geometries;
    }

    public void loadInputToStore() {
        if (geocodingProperties.isRefreshStore()) {
            List<String> locations = inputService.getLocations(geocodingProperties.getInputSource());
            logger.info("Got {} locations from input.", locations.size());
            geometryStoreService.removeAll();
            List<Geometry> geometries = getGeometriesFromLocations(locations);
            logger.info("Got {} geometries for {} locations.", geometries.size(), locations.size());
            geometryStoreService.insertAll(geometries);
        }
    }

    List<Geometry> getGeometriesFromLocations(List<String> locations) {
        return locations.parallelStream()
                .map(address -> {
                    GeocodeGeometryDto geometry = geocodeRepository.getGeometry(address);
                    if (geometry != null) {
                        return geometryStoreMapper.toDBGeometry(address, geometry);
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
