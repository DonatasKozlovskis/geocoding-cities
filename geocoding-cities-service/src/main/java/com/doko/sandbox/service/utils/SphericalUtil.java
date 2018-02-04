package com.doko.sandbox.service.utils;

import static java.lang.Math.*;


public class SphericalUtil {

    // The earth's radius, in kilometers.
    static final double EARTH_RADIUS = 6371.009;

    private SphericalUtil() {
    }

    // Returns the distance between two Lat Lng points, in kilometers.
    public static double computeDistanceBetween(Double fromLat, Double fromLng, Double toLat, Double toLng) {
        return computeAngleBetween(fromLat, fromLng, toLat, toLng) * EARTH_RADIUS;
    }

    /**
     * Returns the angle between two LatLngs, in radians. This is the same as the distance
     * on the unit sphere.
     */
    static double computeAngleBetween(Double fromLat, Double fromLng, Double toLat, Double toLng) {
        return distanceRadians(toRadians(fromLat), toRadians(fromLng),
                toRadians(toLat), toRadians(toLng));
    }

    /**
     * Returns distance on the unit sphere; the arguments are in radians.
     */
    private static double distanceRadians(double lat1, double lng1, double lat2, double lng2) {
        return arcHav(havDistance(lat1, lat2, lng1 - lng2));
    }

    /**
     * Returns hav() of distance from (lat1, lng1) to (lat2, lng2) on the unit sphere.
     */
    static double havDistance(double lat1, double lat2, double dLng) {
        return hav(lat1 - lat2) + hav(dLng) * cos(lat1) * cos(lat2);
    }

    /**
     * Returns haversine(angle-in-radians).
     * hav(x) == (1 - cos(x)) / 2 == sin(x / 2)^2.
     */
    static double hav(double x) {
        double sinHalf = sin(x * 0.5);
        return sinHalf * sinHalf;
    }

    /**
     * Computes inverse haversine. Has good numerical stability around 0.
     * arcHav(x) == acos(1 - 2 * x) == 2 * asin(sqrt(x)).
     * The argument must be in [0, 1], and the result is positive.
     */
    static double arcHav(double x) {
        return 2 * asin(sqrt(x));
    }
}