package com.doko.sandbox.store;

import java.util.List;

public class DefaultGeometryStoreService implements GeometryStoreService {
    private final GeometryDAO geometryDAO;
    private final int closestLimit;

    public DefaultGeometryStoreService(GeometryDAO geometryDAO, int closestLimit) {
        this.geometryDAO = geometryDAO;
        this.closestLimit = closestLimit;
    }

    @Override
    public void insertAll(List<Geometry> geometries) {
        geometryDAO.insertAll(geometries);
    }

    @Override
    public void removeAll() {
        geometryDAO.deleteAll();
    }

    @Override
    public List<Geometry> findByLocation(Double lat, Double lng) {
        return geometryDAO.findByLocation(lat, lng);
    }

    @Override
    public List<Geometry> findClosest(Double lat, Double lng) {
        return geometryDAO.findClosest(lat, lng, closestLimit);
    }

    @Override
    public List<Geometry> findAll() {
        return geometryDAO.findAll();
    }
}
