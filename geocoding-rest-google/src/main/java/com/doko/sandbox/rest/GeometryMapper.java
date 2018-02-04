package com.doko.sandbox.rest;

import com.doko.google.rest.model.Geometry;

public class GeometryMapper {

    public GeocodeGeometryDto assembleGeometryDto(Geometry geometry) {
        GeocodeGeometryDto geocodeGeometryDto = new GeocodeGeometryDto();

        geocodeGeometryDto.setLat(geometry.getLocation().getLat());
        geocodeGeometryDto.setLng(geometry.getLocation().getLng());

        geocodeGeometryDto.setNortheastLat(geometry.getViewport().getNortheast().getLat());
        geocodeGeometryDto.setNortheastLng(geometry.getViewport().getNortheast().getLng());

        geocodeGeometryDto.setSouthwestLat(geometry.getViewport().getSouthwest().getLat());
        geocodeGeometryDto.setSouthwestLng(geometry.getViewport().getSouthwest().getLng());

        return geocodeGeometryDto;
    }
}
