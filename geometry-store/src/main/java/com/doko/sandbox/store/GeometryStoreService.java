package com.doko.sandbox.store;

import java.util.List;

public interface GeometryStoreService {

    void insertAll(List<Geometry> geometries);

    void removeAll();

    List<Geometry> findByLocation(Double lat, Double lng);

    List<Geometry> findClosest(Double lat, Double lng);

    List<Geometry> findAll();
}
