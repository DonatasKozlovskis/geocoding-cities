package com.doko.sandbox.rest;

import com.doko.google.rest.model.GeocodeResponse;
import com.doko.google.rest.model.Geometry;
import com.doko.google.rest.model.Result;
import com.doko.sandbox.rest.configuration.GoogleProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GoogleGeocodeRepositoryTest {
    @Mock
    GoogleProperties googlePropertiesMock;
    @Mock
    RestTemplate restTemplateMock;
    @Mock
    GeometryMapper geometryMapperMock;

    @Test
    public void getGeometry_nullResultInResponse_shouldReturnNull() throws Exception {
        //Arrange
        when(restTemplateMock.getForObject(anyString(), eq(GeocodeResponse.class), anyString(), anyString()))
                .thenReturn(new GeocodeResponse());

        GeocodeRepository geocodeRepository = new GoogleGeocodeRepository(googlePropertiesMock, restTemplateMock, geometryMapperMock);
        //Act
        GeocodeGeometryDto testDto = geocodeRepository.getGeometry("Test");
        //Assert
        verify(googlePropertiesMock).getApiKey();
        verify(restTemplateMock).getForObject(anyString(), eq(GeocodeResponse.class), anyString(), anyString());
        verify(geometryMapperMock, times(0)).assembleGeometryDto(any());

        Assert.assertNull(testDto);
    }

    @Test
    public void getGeometry_resultInResponse_shouldReturnGeocodeGeometryDto() throws Exception {
        //Arrange
        when(restTemplateMock.getForObject(anyString(), eq(GeocodeResponse.class), anyString(), anyString()))
                .thenReturn(geocodeResponse());
        when(geometryMapperMock.assembleGeometryDto(any()))
                .thenReturn(new GeocodeGeometryDto());

        GeocodeRepository geocodeRepository = new GoogleGeocodeRepository(googlePropertiesMock, restTemplateMock, geometryMapperMock);
        //Act
        GeocodeGeometryDto testDto = geocodeRepository.getGeometry("Test");
        //Assert
        verify(googlePropertiesMock).getApiKey();
        verify(restTemplateMock).getForObject(anyString(), eq(GeocodeResponse.class), anyString(), anyString());
        verify(geometryMapperMock, times(1)).assembleGeometryDto(any());

        Assert.assertNotNull(testDto);
    }

    private GeocodeResponse geocodeResponse() {
        GeocodeResponse geocodeResponse = new GeocodeResponse();

        Result result = new Result();
        result.setGeometry(new Geometry());
        geocodeResponse.setResult(result);
        return geocodeResponse;
    }
}