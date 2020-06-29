package com.dwp.usersbycity.service;

import com.dwp.usersbycity.config.ExternalApiProperties;
import com.dwp.usersbycity.config.GeoProperties;
import com.dwp.usersbycity.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserLookupServiceTests {

    UserLookupService userLookupService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GeoProperties geoProperties;

    @Mock
    private ExternalApiProperties externalApiProperties;

    private List<User> usersLinkedToCity = new ArrayList<>();

    User[] usersLivingInCity = new User[1];
    User[] allUsers= new User[3];

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        userLookupService = new UserLookupService(restTemplate, geoProperties, externalApiProperties);

        Mockito.when(externalApiProperties.getBaseUrl()).thenReturn("BASE");
        Mockito.when(externalApiProperties.getUsersLivingInCityEndpoint()).thenReturn("/LIVING_IN_CITY");
        Mockito.when(externalApiProperties.getAllUsersEndpoint()).thenReturn("/ALL_USERS");
        Mockito.when(geoProperties.getCityLat()).thenReturn(51.5072);
        Mockito.when(geoProperties.getCityLon()).thenReturn(-0.1275);
        Mockito.when(geoProperties.getRadius()).thenReturn(50);
        Mockito.when(geoProperties.getMilesPerMetre()).thenReturn(0.0006213712);

        Mockito.when(restTemplate.getForEntity(eq("BASE/LIVING_IN_CITY"), eq(User[].class)))
                .thenReturn(new ResponseEntity<>(usersLivingInCity, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity(eq("BASE/ALL_USERS"), eq(User[].class)))
                .thenReturn(new ResponseEntity<>(allUsers, HttpStatus.OK));
    }

    @Test
    public void testGetUsersLinkedToCity() {
        this.setupUsers();
        userLookupService.getUsersLinkedToCity();
        assertEquals(2, usersLinkedToCity.size());
    }

    @Test
    public void testGetUsersLinkedToCityNoUsers() {
        this.setupUsersForNoUsersResult();
        userLookupService.getUsersLinkedToCity();

    }

    private void setupUsers() {
        User userLivingInCity = new User();
        userLivingInCity.setId("1");
        userLivingInCity.setLatitude("1");
        userLivingInCity.setLongitude("-3");

        User userInCity = new User();
        userInCity.setId("2");
        userInCity.setLatitude("51.7");
        userInCity.setLongitude("-0.132");

        User userNotLinkedToCity = new User();
        userNotLinkedToCity.setId("3");
        userNotLinkedToCity.setLatitude("34");
        userNotLinkedToCity.setLongitude("4");

        usersLivingInCity[0] = userLivingInCity;
        allUsers[0] = userLivingInCity;
        allUsers[1] = userInCity;
        allUsers[2] = userNotLinkedToCity;
    }

    private void setupUsersForNoUsersResult() {
        User userLivingInCity = new User();
        userLivingInCity.setId("1");
        userLivingInCity.setLatitude("1");
        userLivingInCity.setLongitude("-3");

        User userInCity = new User();
        userInCity.setId("2");
        userInCity.setLatitude("10.7");
        userInCity.setLongitude("-47.132");

        User userNotLinkedToCity = new User();
        userNotLinkedToCity.setId("3");
        userNotLinkedToCity.setLatitude("34");
        userNotLinkedToCity.setLongitude("4");

        allUsers[0] = userLivingInCity;
        allUsers[1] = userInCity;
        allUsers[2] = userNotLinkedToCity;
    }
}
