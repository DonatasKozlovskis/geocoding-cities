package com.doko.sandbox.service;

import com.doko.sandbox.input.InputService;
import com.doko.sandbox.rest.GeocodeGeometryDto;
import com.doko.sandbox.rest.GeocodeRepository;
import com.doko.sandbox.service.configuration.GeocodingProperties;
import com.doko.sandbox.store.Geometry;
import com.doko.sandbox.store.GeometryStoreService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GeocodingServiceTest {

    @Mock
    private GeocodingProperties geocitiesPropertiesMock;
    @Mock
    private InputService inputServiceMock;
    @Mock
    private GeocodeRepository geocodeRepositoryMock;
    @Mock
    private GeometryStoreService geometryStoreServiceMock;
    @Mock
    private GeometryStoreMapper geometryStoreMapperMock;

    @Test
    public void getClosestLocations_latLonByLocationFound_byLocationReturned() {
        GeocodingService geocitiesService = new GeocodingService(null, null, null, geometryStoreServiceMock, null);
        List<Geometry> closestGeometries = Arrays.asList(new Geometry());
        when(geometryStoreServiceMock.findByLocation(any(), any())).thenReturn(closestGeometries);

        List<Geometry> closestLocations = geocitiesService.getClosestLocations(1.0, 2.0);

        verify(geometryStoreServiceMock).findByLocation(1.0, 2.0);
        verify(geometryStoreServiceMock, times(0)).findClosest(any(), any());
        Assert.assertEquals(closestGeometries.size(), closestLocations.size());
        Assert.assertEquals(closestGeometries.get(0), closestLocations.get(0));
    }

    @Test
    public void getClosestLocations_latLonByLocationNotFound_closestReturned() {
        GeocodingService geocitiesService = new GeocodingService(null, null, null, geometryStoreServiceMock, null);
        List<Geometry> closestGeometries = Arrays.asList(new Geometry());
        when(geometryStoreServiceMock.findByLocation(any(), any())).thenReturn(Collections.emptyList());
        when(geometryStoreServiceMock.findClosest(any(), any())).thenReturn(closestGeometries);


        List<Geometry> closestLocations = geocitiesService.getClosestLocations(1.0, 2.0);

        verify(geometryStoreServiceMock).findByLocation(1.0, 2.0);
        verify(geometryStoreServiceMock).findClosest(1.0, 2.0);

        Assert.assertEquals(closestGeometries.size(), closestLocations.size());
        Assert.assertEquals(closestGeometries.get(0), closestLocations.get(0));
    }

    @Test
    public void loadInputToStore_noRefresh_dataIsNotRefreshed() {
        GeocodingService geocitiesServiceSpy = spy(new GeocodingService(
                geocitiesPropertiesMock,
                inputServiceMock,
                null,
                geometryStoreServiceMock,
                null));

        when(geocitiesPropertiesMock.isRefreshStore()).thenReturn(false);

        geocitiesServiceSpy.loadInputToStore();

        verify(geocitiesPropertiesMock).isRefreshStore();
        verify(inputServiceMock, times(0)).getLocations(anyString());
        verify(geometryStoreServiceMock, times(0)).removeAll();
        verify(geometryStoreServiceMock, times(0)).insertAll(anyListOf(Geometry.class));
        verify(geocitiesServiceSpy, times(0)).getGeometriesFromLocations(anyListOf(String.class));
    }

    @Test
    public void loadInputToStore_onRefresh_dataIsRefreshed() {
        //Arrange
        GeocodingService geocitiesServiceSpy = spy(new GeocodingService(
                geocitiesPropertiesMock,
                inputServiceMock,
                null,
                geometryStoreServiceMock,
                null));
        String inputSource = "source";

        when(geocitiesPropertiesMock.isRefreshStore()).thenReturn(true);
        when(geocitiesPropertiesMock.getInputSource()).thenReturn(inputSource);
        when(inputServiceMock.getLocations(anyString())).thenReturn(Arrays.asList("address"));
        doNothing().when(geometryStoreServiceMock).removeAll();
        doReturn(Arrays.asList(new Geometry())).when(geocitiesServiceSpy).getGeometriesFromLocations(anyList());
        doNothing().when(geometryStoreServiceMock).insertAll(anyList());

        //Act
        geocitiesServiceSpy.loadInputToStore();

        //Assert
        verify(geocitiesPropertiesMock).isRefreshStore();
        verify(geocitiesPropertiesMock).getInputSource();

        verify(inputServiceMock, times(1)).getLocations(inputSource);
        verify(geometryStoreServiceMock, times(1)).removeAll();
        verify(geometryStoreServiceMock, times(1)).insertAll(anyList());
        verify(geocitiesServiceSpy, times(1)).getGeometriesFromLocations(anyList());
    }

    @Test
    public void getGeometriesFromLocations_emptyList_returnsEmptyList() {
        GeocodingService geocitiesService = new GeocodingService(
                null,
                null,
                geocodeRepositoryMock,
                null,
                geometryStoreMapperMock);


        List<Geometry> geometriesFromLocations = geocitiesService.getGeometriesFromLocations(Collections.emptyList());

        verify(geocodeRepositoryMock, times(0)).getGeometry(anyString());
        verify(geometryStoreMapperMock, times(0)).toDBGeometry(anyString(), any());

        Assert.assertTrue(geometriesFromLocations.isEmpty());
    }

    @Test
    public void getGeometriesFromLocations_twoElementsInList_twoInResultList() {
        GeocodingService geocitiesService = new GeocodingService(
                null,
                null,
                geocodeRepositoryMock,
                null,
                geometryStoreMapperMock);

        when(geocodeRepositoryMock.getGeometry(anyString())).thenReturn(new GeocodeGeometryDto());
        when(geometryStoreMapperMock.toDBGeometry(anyString(), any())).thenReturn(new Geometry());

        List<Geometry> geometriesFromLocations = geocitiesService.getGeometriesFromLocations(Arrays.asList("one", "two"));

        verify(geocodeRepositoryMock, times(2)).getGeometry(anyString());
        verify(geometryStoreMapperMock, times(2)).toDBGeometry(anyString(), any());

        Assert.assertEquals(2, geometriesFromLocations.size());
    }
}