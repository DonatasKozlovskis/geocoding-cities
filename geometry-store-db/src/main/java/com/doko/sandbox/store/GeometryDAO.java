package com.doko.sandbox.store;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

public interface GeometryDAO {
    String ADDRESS = "address";
    String LAT = "lat";
    String LNG = "lng";
    String SOUTHWEST_LAT = "southwestlat";
    String SOUTHWEST_LNG = "southwestlng";
    String NORTHEAST_LAT = "northeastlat";
    String NORTHEAST_LNG = "northeastlng";

    @SqlBatch("INSERT INTO geometry (address, lat, lng, southwestlat, southwestlng, northeastlat, northeastlng) " +
            "VALUES (:address, :lat, :lng, :southwestLat, :southwestLng, :northeastLat, :northeastLng)")
    @BatchChunkSize(1000)
    void insertAll(@BindBean List<Geometry> geometries);

    @SqlUpdate("TRUNCATE TABLE geometry")
    void deleteAll();

    @SqlQuery("SELECT * FROM geometry " +
            "WHERE southwestlat <= :lat  AND northeastlat >= :lat AND southwestlng  <= :lng AND northeastlng >= :lng")
    @Mapper(GeometryMapper.class)
    List<Geometry> findByLocation(@Bind("lat") Double lat, @Bind("lng") Double lng);

    @SqlQuery("SELECT *, (POWER((:lat-lat),2)+POWER((:lng-lng)*COS(lat/57.3),2)) as approxDist " +
            "FROM GEOMETRY " +
            "ORDER BY approxDist ASC " +
            "LIMIT :limit")
    @Mapper(GeometryMapper.class)
    List<Geometry> findClosest(@Bind("lat") Double lat, @Bind("lng") Double lng, @Bind("limit") int limit);

    @SqlQuery("SELECT * FROM geometry")
    @Mapper(GeometryMapper.class)
    List<Geometry> findAll();


}
