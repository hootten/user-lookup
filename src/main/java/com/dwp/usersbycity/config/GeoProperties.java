package com.dwp.usersbycity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "geo")
public class GeoProperties {

    private final String CITY;
    private final double CITY_LAT;
    private final double CITY_LON;
    private final double MILES_PER_METRE;
    private final int RADIUS;
    private final double LAT_MAX;
    private final double LON_MAX;
    private final double LAT_MIN;
    private final double LON_MIN;

    public GeoProperties(String city, double cityLat, double cityLon, double milesPerMetre, int radius, int lat_max, int lon_max, double lat_min, double lon_min) {
        this.CITY = city;
        this.CITY_LAT = cityLat;
        this.CITY_LON = cityLon;
        this.MILES_PER_METRE = milesPerMetre;
        this.RADIUS = radius;
        this.LAT_MAX = lat_max;
        this.LON_MAX = lon_max;
        this.LAT_MIN = lat_min;
        this.LON_MIN = lon_min;
    }

    public double getMilesPerMetre() {
        return MILES_PER_METRE;
    }

    public int getRadius() {
        return RADIUS;
    }

    public String getCity() {
        return CITY;
    }

    public double getCityLat() {
        return CITY_LAT;
    }

    public double getCityLon() {
        return CITY_LON;
    }

    public double getLatMax() {
        return LAT_MAX;
    }

    public double getLatMin() {
        return LAT_MIN;
    }

    public double getLonMin() {
        return LON_MIN;
    }

    public double getLonMax() {
        return LON_MAX;
    }
}
