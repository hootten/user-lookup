package com.dwp.usersbycity.service;

import com.dwp.usersbycity.config.ExternalApiProperties;
import com.dwp.usersbycity.config.GeoProperties;
import com.dwp.usersbycity.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import net.sf.geographiclib.*;

import java.util.*;

@Service
public class UserLookupService {

    @Autowired
    private RestTemplate restTemplate;

    private final GeoProperties geoProperties;
    private final ExternalApiProperties externalApiProperties;
    private final List<User> cityUsers = new ArrayList<>();

    public UserLookupService(GeoProperties geoProperties,
                             ExternalApiProperties externalApiProperties) {
        this.geoProperties = geoProperties;
        this.externalApiProperties = externalApiProperties;
    }

    public ResponseEntity<List<User>> getUsersLinkedToCity() {
        this.addUsersLivingInCity();
        this.addUsersInRadiusOfCity();
        return ResponseEntity.ok(cityUsers);
    }

    private void addUsersLivingInCity() {
        String url = externalApiProperties.getBaseUrl() + externalApiProperties.getUsersLivingInCityEndpoint();
        ResponseEntity<User[]> response = this.restTemplate.getForEntity(url, User[].class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<User> usersLivingInCity = new ArrayList<>();
            Collections.addAll(usersLivingInCity, response.getBody());
            cityUsers.addAll(usersLivingInCity);
        }
    }

    private void addUsersInRadiusOfCity() {
        String url = externalApiProperties.getBaseUrl() + externalApiProperties.getAllUsersEndpoint();
        ResponseEntity<User[]> response = this.restTemplate.getForEntity(url, User[].class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<User> allUsers = Arrays.asList(response.getBody());
            allUsers.removeIf(user -> user.getId() == -1);
            List<User> usersWithinRadius = new ArrayList<>();
            allUsers.forEach( user -> {
                GeodesicData geodesicData = Geodesic.WGS84.
                        Inverse(geoProperties.getCityLat(), geoProperties.getCityLon(), user.getLatitude(), user.getLongitude());
                double distanceInMetres = geodesicData.s12;
                double distanceInMiles = distanceInMetres * geoProperties.getMilesPerMetre();
                if (distanceInMiles <= geoProperties.getRadius()) {
                    usersWithinRadius.add(user);
                }
            });
            cityUsers.addAll(usersWithinRadius);
        }
    }
}
