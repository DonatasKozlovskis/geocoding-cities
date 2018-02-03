package com.doko.sandbox.rest;

import com.doko.google.rest.model.Bounds;
import com.doko.google.rest.model.Geometry;
import com.doko.google.rest.model.LatLng;
import org.junit.Assert;
import org.junit.Test;

public class GeometryMapperTest {

    @Test
    public void assembleGeometryDto_geometryWithAllFields_fullGeometryDto() throws Exception {
        GeometryMapper geometryMapper = new GeometryMapper();
        //Act
        GeocodeGeometryDto geocodeGeometryDto = geometryMapper.assembleGeometryDto(fullGeometry());
        //Assert
        Assert.assertEquals(geocodeGeometryDto.getLat(), fullGeometry().getLocation().getLat(), 0);
        Assert.assertEquals(geocodeGeometryDto.getLng(), fullGeometry().getLocation().getLng(), 0);
        Assert.assertEquals(geocodeGeometryDto.getNortheastLat(), fullGeometry().getViewport().getNortheast().getLat(), 0);
        Assert.assertEquals(geocodeGeometryDto.getNortheastLng(), fullGeometry().getViewport().getNortheast().getLat(), 0);
        Assert.assertEquals(geocodeGeometryDto.getSouthwestLat(), fullGeometry().getViewport().getSouthwest().getLat(), 0);
        Assert.assertEquals(geocodeGeometryDto.getSouthwestLng(), fullGeometry().getViewport().getSouthwest().getLat(), 0);
    }

    private Geometry fullGeometry() {
        LatLng latLng = new LatLng();
        latLng.setLat(1);
        latLng.setLng(2);

        Bounds bounds = new Bounds();
        LatLng latLngNorth = new LatLng();
        latLng.setLat(11);
        latLng.setLng(22);
        bounds.setNortheast(latLngNorth);

        LatLng latLngSouth = new LatLng();
        latLng.setLat(33);
        latLng.setLng(44);
        bounds.setSouthwest(latLngSouth);

        Geometry geometry = new Geometry();
        geometry.setLocation(latLng);
        geometry.setViewport(bounds);

        return geometry;
    }
}