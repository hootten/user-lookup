package com.dwp.usersbycity.service;

import com.dwp.usersbycity.exceptions.UnableToAccessExternalApiException;
import com.dwp.usersbycity.utils.UserLookupTestsUtils;
import com.dwp.usersbycity.config.ExternalApiProperties;
import com.dwp.usersbycity.config.GeoProperties;
import com.dwp.usersbycity.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserLookupServiceTests {

    private UserLookupService userLookupService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GeoProperties geoProperties;

    @Mock
    private GeoService geoService;

    @Mock
    private ExternalApiProperties externalApiProperties;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        userLookupService = new UserLookupService(restTemplate, geoProperties, externalApiProperties, geoService);

        when(externalApiProperties.getBaseUrl()).thenReturn("BASE");
        when(externalApiProperties.getUsersLivingInCityEndpoint()).thenReturn("/LIVING_IN_CITY");
        when(externalApiProperties.getAllUsersEndpoint()).thenReturn("/ALL_USERS");
        when(geoProperties.getCityLat()).thenReturn(UserLookupTestsUtils.CITY_LAT);
        when(geoProperties.getCityLon()).thenReturn(UserLookupTestsUtils.CITY_LON);
        when(geoProperties.getRadius()).thenReturn(UserLookupTestsUtils.RADIUS);
        when(geoProperties.getLonMax()).thenReturn(UserLookupTestsUtils.MAX_LON);
        when(geoProperties.getLonMin()).thenReturn(UserLookupTestsUtils.MIN_LON);
        when(geoProperties.getLatMax()).thenReturn(UserLookupTestsUtils.MAX_LAT);
        when(geoProperties.getLatMin()).thenReturn(UserLookupTestsUtils.MIN_LON);
    }

    @Test
    public void testGetUsersLivingInCity() {
        User[] usersLivingInCity = new User[1];
        this.setupUsersLivingInCity(usersLivingInCity);

        ResponseEntity<User[]> response = new ResponseEntity<>(usersLivingInCity, HttpStatus.OK);
        when(restTemplate.getForEntity(eq("BASE/LIVING_IN_CITY"), eq(User[].class)))
                .thenReturn(response);

        userLookupService.getUsersLivingInCity();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), usersLivingInCity);
    }

    @Test
    public void testGetUsersInRadiusOfCity() {
        User[] allUsers = new User[3];
        this.setupAllUsers(allUsers);

        ResponseEntity<User[]> response = new ResponseEntity<>(allUsers, HttpStatus.OK);
        when(restTemplate.getForEntity(eq("BASE/ALL_USERS"), eq(User[].class)))
                .thenReturn(response);

        userLookupService.getUsersInRadiusOfCity();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), allUsers);
    }


    @Test
    public void testGetNoUsersLivingInCity() {
        User[] usersLivingInCity = new User[0];

        ResponseEntity<User[]> response = new ResponseEntity<>(usersLivingInCity, HttpStatus.OK);
        when(restTemplate.getForEntity(eq("BASE/LIVING_IN_CITY"), eq(User[].class)))
                .thenReturn(response);

        userLookupService.getUsersLivingInCity();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), usersLivingInCity);
    }

    @Test
    public void testGetNoUsersInRadiusOfCity() {
        User[] allUsers = new User[2];
        this.setupUsersForNoUsersInRadiusResult(allUsers);

        ResponseEntity<User[]> response = new ResponseEntity<>(allUsers, HttpStatus.OK);
        when(restTemplate.getForEntity(eq("BASE/ALL_USERS"), eq(User[].class)))
                .thenReturn(response);

        userLookupService.getUsersInRadiusOfCity();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), allUsers);
    }

    @Test
    public void testGetUsersLivingInCityBadResponse() {
        ResponseEntity<User[]> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.getForEntity(eq("BASE/LIVING_IN_CITY"), eq(User[].class)))
                .thenReturn(response);
        assertThatThrownBy(() -> userLookupService.getUsersLivingInCity())
                .isInstanceOf(UnableToAccessExternalApiException.class);
    }

    @Test
    public void testGetUsersInRadiusOfCityBadResponse() {
        ResponseEntity<User[]> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.getForEntity(eq("BASE/ALL_USERS"), eq(User[].class)))
                .thenReturn(response);
        assertThatThrownBy(() -> userLookupService.getUsersInRadiusOfCity())
                .isInstanceOf(UnableToAccessExternalApiException.class);
    }

    private void setupUsersLivingInCity(User[] usersLivingInCity) {
        usersLivingInCity[0] = UserLookupTestsUtils.getUserLivingInCity();
    }

    public void setupAllUsers(User[] allUsers) {
        allUsers[0] = UserLookupTestsUtils.getUserLivingInCity();
        allUsers[1] = UserLookupTestsUtils.getUserInCity();
        allUsers[2] = UserLookupTestsUtils.getUserNotLinkedToCity();
    }

    private void setupUsersForNoUsersInRadiusResult(User[] allUsers) {
        allUsers[0] = UserLookupTestsUtils.getUserLivingInCity();
        allUsers[1] = UserLookupTestsUtils.getUserNotLinkedToCity();
    }

}
