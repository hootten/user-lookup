package com.dwp.usersbycity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "geo")
public class GeoProperties {

    private final String city;
    private final double cityLat;
    private final double cityLon;
    private final double milesPerMetre;
    private final int radius;

    public GeoProperties(String city, double cityLat, double cityLon, double milesPerMetre, int radius) {
        this.city = city;
        this.cityLat = cityLat;
        this.cityLon = cityLon;
        this.milesPerMetre = milesPerMetre;
        this.radius = radius;
    }

    public double getMilesPerMetre() {
        return milesPerMetre;
    }

    public int getRadius() {
        return radius;
    }

    public String getCity() {
        return city;
    }

    public double getCityLat() {
        return cityLat;
    }

    public double getCityLon() {
        return cityLon;
    }
}
