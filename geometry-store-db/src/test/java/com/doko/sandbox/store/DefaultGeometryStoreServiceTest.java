package com.doko.sandbox.store;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class DefaultGeometryStoreServiceTest {
    private static final int CLOSEST_LIMIT = 4;
    private GeometryDAO geometryDAOMock = mock(GeometryDAO.class);

    @Test
    public void insertAll_inputList_daoCalled() throws Exception {
        doNothing().when(geometryDAOMock).insertAll(anyList());
        GeometryStoreService defaultGeometryStoreService = new DefaultGeometryStoreService(geometryDAOMock, CLOSEST_LIMIT);

        List<Geometry> geometries = Arrays.asList(new Geometry());
        defaultGeometryStoreService.insertAll(geometries);

        verify(geometryDAOMock, times(1)).insertAll(geometries);
    }

    @Test
    public void removeAll_noInput_daoCalled() throws Exception {
        doNothing().when(geometryDAOMock).deleteAll();
        GeometryStoreService defaultGeometryStoreService = new DefaultGeometryStoreService(geometryDAOMock, CLOSEST_LIMIT);

        defaultGeometryStoreService.removeAll();

        verify(geometryDAOMock, times(1)).deleteAll();
    }

    @Test
    public void findByLocation_latLonGiven_daoCalled() throws Exception {
        List<Geometry> geometries = Arrays.asList(new Geometry());
        when(geometryDAOMock.findByLocation(anyDouble(), anyDouble())).thenReturn(geometries);

        GeometryStoreService defaultGeometryStoreService = new DefaultGeometryStoreService(geometryDAOMock, CLOSEST_LIMIT);

        List<Geometry> byLocation = defaultGeometryStoreService.findByLocation(1.0, 2.0);

        verify(geometryDAOMock, times(1)).findByLocation(1.0, 2.0);

        Assert.assertEquals(geometries.size(), byLocation.size());
        Assert.assertEquals(geometries.get(0), byLocation.get(0));
    }

    @Test
    public void findClosest_latLonGiven_daoCalled() throws Exception {
        List<Geometry> geometries = Arrays.asList(new Geometry());
        when(geometryDAOMock.findClosest(anyDouble(), anyDouble(), anyInt())).thenReturn(geometries);

        GeometryStoreService defaultGeometryStoreService = new DefaultGeometryStoreService(geometryDAOMock, CLOSEST_LIMIT);

        List<Geometry> closest = defaultGeometryStoreService.findClosest(1.0, 2.0);

        verify(geometryDAOMock, times(1)).findClosest(1.0, 2.0, CLOSEST_LIMIT);

        Assert.assertEquals(geometries.size(), closest.size());
        Assert.assertEquals(geometries.get(0), closest.get(0));
    }

    @Test
    public void findAll() throws Exception {
        List<Geometry> geometries = Arrays.asList(new Geometry());
        when(geometryDAOMock.findAll()).thenReturn(geometries);

        GeometryStoreService defaultGeometryStoreService = new DefaultGeometryStoreService(geometryDAOMock, CLOSEST_LIMIT);

        List<Geometry> all = defaultGeometryStoreService.findAll();

        verify(geometryDAOMock, times(1)).findAll();

        Assert.assertEquals(geometries.size(), all.size());
        Assert.assertEquals(geometries.get(0), all.get(0));
    }

}