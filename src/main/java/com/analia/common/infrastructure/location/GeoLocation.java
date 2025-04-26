package com.analia.common.infrastructure.location;

import com.analia.common.model.AnaliaEntity;

import java.util.List;

public abstract class GeoLocation extends AnaliaEntity {
    private static final double DEFAULT_VALUE = 10000.0;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private double distance;

    /**
     * @param latitude
     * @param longitude
     * @return
     */
    public synchronized static <T extends GeoLocation> List<T> getDistances(List<T> tradeResultSet, double latitude, double longitude) {
        if (tradeResultSet == null || tradeResultSet.isEmpty()) {
            return tradeResultSet;
        }
        for (int i = 0; i < tradeResultSet.size(); i++) {
            GeoLocation geoLocation = tradeResultSet.get(i);
            double distance = computeDistanceBetweenTwoPoints(geoLocation.getLatitude(), geoLocation.getLongitude(), latitude, longitude);
            geoLocation.setDistance(distance);
        }
        return tradeResultSet;
    }

    /**
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @param radius
     * @return
     */
    public static boolean isOutSideOfTheRadius(double lat1, double lon1, double lat2, double lon2, int radius) {
        double distance = computeDistanceBetweenTwoPoints(lat1, lon1, lat2, lon2);
        return distance > radius;
    }

    /**
     * @param latitude1
     * @param longitude1
     * @param latitude2
     * @param longitude2
     * @return
     */
    public static double computeDistanceBetweenTwoPoints(double latitude1, double longitude1, double latitude2, double longitude2) {
        double dLat = deg2rad(latitude2 - latitude1);
        double dLong = deg2rad(longitude2 - longitude1);
        double delta = (Math.sin(dLat / 2) * Math.sin(dLat / 2)) + (Math.sin(dLong / 2) * Math.sin(dLong / 2) * Math.cos(deg2rad(latitude1) * Math.cos(deg2rad(latitude2))));
        if (delta < 1.0) {
            double dist = 2 * 6371 * Math.atan2(Math.sqrt(delta), Math.sqrt(1 - delta));
            return dist;
        }
        return DEFAULT_VALUE;
    }

    /**
     * @param deg
     * @return
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public abstract double getLongitude();

    public abstract double getLatitude();

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}
