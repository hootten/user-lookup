package com.dwp.usersbycity.service;

import com.dwp.usersbycity.config.ExternalApiProperties;
import com.dwp.usersbycity.config.GeoProperties;
import com.dwp.usersbycity.controller.Constants;
import com.dwp.usersbycity.exceptions.InvalidCityCoordinateException;
import com.dwp.usersbycity.exceptions.UnableToAccessExternalApiException;
import com.dwp.usersbycity.models.Axis;
import com.dwp.usersbycity.models.CoordinateOf;
import com.dwp.usersbycity.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.List;

@Service
public class UserLookupService {

    private final RestTemplate restTemplate;
    private final GeoProperties geoProperties;
    private final ExternalApiProperties externalApiProperties;
    private final GeoService geoService;

    private final Logger LOGGER = LoggerFactory.getLogger(UserLookupService.class);

    @Autowired
    public UserLookupService(RestTemplate restTemplate, GeoProperties geoProperties,
                             ExternalApiProperties externalApiProperties, GeoService geoService) {
        this.restTemplate = restTemplate;
        this.geoProperties = geoProperties;
        this.externalApiProperties = externalApiProperties;
        this.geoService = geoService;
    }

    public ResponseEntity<List<User>> getUsersLivingInCity() {
        LOGGER.info("Retrieving users living in " + geoProperties.getCity());
        String url = externalApiProperties.getBaseUrl() + externalApiProperties.getUsersLivingInCityEndpoint();
        ResponseEntity<User[]> response = this.restTemplate.getForEntity(url, User[].class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<User> usersLivingInCity = new ArrayList<>();
            Collections.addAll(usersLivingInCity, response.getBody());
            return ResponseEntity.ok(usersLivingInCity);
        } else {
            throw new UnableToAccessExternalApiException(
                    "Failed to retrieve users living in city from external API.");
        }
    }

    public ResponseEntity<List<User>> getUsersInRadiusOfCity() {
        LOGGER.info("Retrieving users within a " + geoProperties.getRadius() + " mile radius of " + geoProperties.getCity());
        String url = externalApiProperties.getBaseUrl() + externalApiProperties.getAllUsersEndpoint();
        ResponseEntity<User[]> response = this.restTemplate.getForEntity(url, User[].class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<User> allUsers = Arrays.asList(response.getBody());
            allUsers.removeIf(user -> user.getId() == Constants.ERROR_ID);
            List<User> usersWithinRadius = new ArrayList<>();
            allUsers.forEach( user -> {
                try {
                    if (this.isUserWithinRadius(user)) {
                        usersWithinRadius.add(user);
                    }
                } catch (IllegalArgumentException e) {
                    LOGGER.error("User " + user.getId() + " skipped: has an invalid coordinate");
                }
            });
            return ResponseEntity.ok(usersWithinRadius);
        } else {
            throw new UnableToAccessExternalApiException(
                    "Failed to retrieve all users from external API.");
        }
    }

    private boolean isUserWithinRadius(User user) throws IllegalArgumentException{
        double cityLat = validateCoordinate(geoProperties.getCityLat(), Axis.LAT, CoordinateOf.CITY);
        double cityLon = validateCoordinate(geoProperties.getCityLon(), Axis.LON, CoordinateOf.CITY);

        double userLat = validateCoordinate(user.getLatitude(), Axis.LAT, CoordinateOf.USER);
        double userLon = validateCoordinate(user.getLongitude(), Axis.LON, CoordinateOf.USER);

        double distanceInMiles = this.geoService.getDistanceInMilesBetweenTwoPoints(cityLat, cityLon,
                                                                                    userLat, userLon);
        return distanceInMiles <= geoProperties.getRadius();
    }

    private double validateCoordinate(double coordinate, Axis axis, CoordinateOf coordinateOf) {
        double min;
        double max;
        if (axis.equals(Axis.LAT)) {
            min = geoProperties.getLatMin();
            max = geoProperties.getLatMax();
        } else {
            min = geoProperties.getLonMin();
            max = geoProperties.getLonMax();
        }
        if (coordinate >= min && coordinate <= max) {
            return coordinate;
        } else {
            if (coordinateOf.equals(CoordinateOf.USER)) {
                throw new IllegalArgumentException("User's coordinates are not valid");
            } else {
                throw new InvalidCityCoordinateException(
                        "Coordinates for " + geoProperties.getCity() + "the given city are not valid");
            }
        }
    }
}
