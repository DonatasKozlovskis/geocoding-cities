package com.doko.sandbox.service;


import com.doko.sandbox.rest.GeocodeGeometryDto;
import com.doko.sandbox.store.Geometry;

public class GeometryStoreMapper {

    public Geometry toDBGeometry(String address, GeocodeGeometryDto geocodeGeometryDto) {
        Geometry result = new Geometry();
        result.setAddress(address);
        result.setLat(geocodeGeometryDto.getLat());
        result.setLng(geocodeGeometryDto.getLng());

        result.setNortheastLat(geocodeGeometryDto.getNortheastLat());
        result.setNortheastLng(geocodeGeometryDto.getNortheastLng());

        result.setSouthwestLat(geocodeGeometryDto.getSouthwestLat());
        result.setSouthwestLng(geocodeGeometryDto.getSouthwestLng());

        return result;
    }
}
