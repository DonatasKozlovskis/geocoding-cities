package com.doko.sandbox.store;

import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;

import static com.doko.sandbox.store.GeometryDAO.*;
import static org.mockito.Mockito.*;

public class GeometryMapperTest {
    @Test
    public void map_resultSetWithData_allFieldsMapped() throws Exception {

        ResultSet rs = mock(ResultSet.class);
        when(rs.getString(ADDRESS)).thenReturn("Vilnius");
        when(rs.getDouble(LAT)).thenReturn(1.0);
        when(rs.getDouble(LNG)).thenReturn(2.0);
        when(rs.getDouble(SOUTHWEST_LAT)).thenReturn(3.0);
        when(rs.getDouble(SOUTHWEST_LNG)).thenReturn(4.0);
        when(rs.getDouble(NORTHEAST_LAT)).thenReturn(5.0);
        when(rs.getDouble(NORTHEAST_LNG)).thenReturn(6.0);

        GeometryMapper geometryMapper = new GeometryMapper();
        Geometry geometryResult = geometryMapper.map(0, rs, null);

        Assert.assertEquals("Vilnius", geometryResult.getAddress());
        Assert.assertEquals(1.0, geometryResult.getLat(), 0);
        Assert.assertEquals(2.0, geometryResult.getLng(), 0);
        Assert.assertEquals(3.0, geometryResult.getSouthwestLat(), 0);
        Assert.assertEquals(4.0, geometryResult.getSouthwestLng(), 0);
        Assert.assertEquals(5.0, geometryResult.getNortheastLat(), 0);
        Assert.assertEquals(6.0, geometryResult.getNortheastLng(), 0);

        verify(rs, times(1)).getString(anyString());
        verify(rs, times(6)).getDouble(anyString());
    }
}