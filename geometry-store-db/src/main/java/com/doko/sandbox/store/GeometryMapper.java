package com.doko.sandbox.store;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.doko.sandbox.store.GeometryDAO.*;


public class GeometryMapper implements ResultSetMapper<Geometry> {
    @Override
    public Geometry map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Geometry geometry = new Geometry();

        geometry.setAddress(r.getString(ADDRESS));
        geometry.setLat(r.getDouble(LAT));
        geometry.setLng(r.getDouble(LNG));

        geometry.setSouthwestLat(r.getDouble(SOUTHWEST_LAT));
        geometry.setSouthwestLng(r.getDouble(SOUTHWEST_LNG));

        geometry.setNortheastLat(r.getDouble(NORTHEAST_LAT));
        geometry.setNortheastLng(r.getDouble(NORTHEAST_LNG));

        return geometry;
    }
}
