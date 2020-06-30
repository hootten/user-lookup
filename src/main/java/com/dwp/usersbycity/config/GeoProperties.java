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

    public GeoProperties(String city, double cityLat, double cityLon, double milesPerMetre, int radius) {
        this.CITY = city;
        this.CITY_LAT = cityLat;
        this.CITY_LON = cityLon;
        this.MILES_PER_METRE = milesPerMetre;
        this.RADIUS = radius;
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

}
