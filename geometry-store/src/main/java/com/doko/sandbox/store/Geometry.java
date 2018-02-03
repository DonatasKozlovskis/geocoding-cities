package com.doko.sandbox.store;

public class Geometry {

    private String address;
    private double lat;
    private double lng;
    private double southwestLat;
    private double southwestLng;
    private double northeastLat;
    private double northeastLng;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getSouthwestLat() {
        return southwestLat;
    }

    public void setSouthwestLat(double southwestLat) {
        this.southwestLat = southwestLat;
    }

    public double getSouthwestLng() {
        return southwestLng;
    }

    public void setSouthwestLng(double southwestLng) {
        this.southwestLng = southwestLng;
    }

    public double getNortheastLat() {
        return northeastLat;
    }

    public void setNortheastLat(double northeastLat) {
        this.northeastLat = northeastLat;
    }

    public double getNortheastLng() {
        return northeastLng;
    }

    public void setNortheastLng(double northeastLng) {
        this.northeastLng = northeastLng;
    }
}
