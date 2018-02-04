package com.doko.sandbox.service;

import com.doko.sandbox.rest.GeocodeGeometryDto;
import com.doko.sandbox.store.Geometry;
import org.junit.Assert;
import org.junit.Test;

public class GeometryStoreMapperTest {
    @Test
    public void toDBGeometry_inputWithAllFields_resultWithallFieldsMapped() {
        GeometryStoreMapper geometryStoreMapper = new GeometryStoreMapper();
        GeocodeGeometryDto geocodeGeometryDto = new GeocodeGeometryDto();
        geocodeGeometryDto.setLat(1);
        geocodeGeometryDto.setLng(2);
        geocodeGeometryDto.setSouthwestLat(3);
        geocodeGeometryDto.setSouthwestLng(4);
        geocodeGeometryDto.setNortheastLat(5);
        geocodeGeometryDto.setNortheastLng(6);
        Geometry result = geometryStoreMapper.toDBGeometry("address", geocodeGeometryDto);

        Assert.assertEquals("address", result.getAddress());
        Assert.assertEquals(1, result.getLat(), 0);
        Assert.assertEquals(2, result.getLng(), 0);
        Assert.assertEquals(3, result.getSouthwestLat(), 0);
        Assert.assertEquals(4, result.getSouthwestLng(), 0);
        Assert.assertEquals(5, result.getNortheastLat(), 0);
        Assert.assertEquals(6, result.getNortheastLng(), 0);

    }
}