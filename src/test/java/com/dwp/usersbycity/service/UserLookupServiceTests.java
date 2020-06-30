package com.dwp.usersbycity.service;

import com.dwp.usersbycity.utils.UserLookupTestsUtils;
import com.dwp.usersbycity.config.ExternalApiProperties;
import com.dwp.usersbycity.config.GeoProperties;
import com.dwp.usersbycity.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserLookupServiceTests {

    private UserLookupService userLookupService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GeoProperties geoProperties;

    @Mock
    private ExternalApiProperties externalApiProperties;

    private ArrayList<User> usersLinkedToCity;

    @Before
    public void init() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        userLookupService = new UserLookupService(restTemplate, geoProperties, externalApiProperties);

        Mockito.when(externalApiProperties.getBaseUrl()).thenReturn("BASE");
        Mockito.when(externalApiProperties.getUsersLivingInCityEndpoint()).thenReturn("/LIVING_IN_CITY");
        Mockito.when(externalApiProperties.getAllUsersEndpoint()).thenReturn("/ALL_USERS");
        Mockito.when(geoProperties.getCityLat()).thenReturn(UserLookupTestsUtils.CITY_LAT);
        Mockito.when(geoProperties.getCityLon()).thenReturn(UserLookupTestsUtils.CITY_LON);
        Mockito.when(geoProperties.getRadius()).thenReturn(UserLookupTestsUtils.RADIUS);
        Mockito.when(geoProperties.getMilesPerMetre()).thenReturn(UserLookupTestsUtils.MILES_PER_METRE);

        Field field = userLookupService.getClass().getDeclaredField("usersLinkedToCity");
        field.setAccessible(true);
        usersLinkedToCity = (ArrayList<User>) field.get(userLookupService);
    }

    @Test
    public void testGetUsersLinkedToCity() {
        User[] usersLivingInCity = new User[1];
        User[] allUsers = new User[3];
        this.setupUsers(allUsers, usersLivingInCity);

        Mockito.when(restTemplate.getForEntity(eq("BASE/LIVING_IN_CITY"), eq(User[].class)))
                .thenReturn(new ResponseEntity<>(usersLivingInCity, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity(eq("BASE/ALL_USERS"), eq(User[].class)))
                .thenReturn(new ResponseEntity<>(allUsers, HttpStatus.OK));

        userLookupService.getUsersLinkedToCity();

        assertEquals(2, usersLinkedToCity.size());
        assertEquals(1, usersLinkedToCity.get(0).getId());
        assertEquals(2, usersLinkedToCity.get(1).getId());
    }

    @Test
    public void testGetUsersLinkedToCityNoUsers() {
        User[] usersLivingInCity = new User[0];
        User[] allUsers = new User[2];
        this.setupUsersForNoUsersResult(allUsers, usersLivingInCity);

        Mockito.when(restTemplate.getForEntity(eq("BASE/LIVING_IN_CITY"), eq(User[].class)))
                .thenReturn(new ResponseEntity<>(usersLivingInCity, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity(eq("BASE/ALL_USERS"), eq(User[].class)))
                .thenReturn(new ResponseEntity<>(allUsers, HttpStatus.OK));

        userLookupService.getUsersLinkedToCity();

        assertEquals(0, usersLinkedToCity.size());
    }

    private void setupUsers(User[] allUsers, User[] usersLivingInCity) {
        usersLivingInCity[0] = UserLookupTestsUtils.getUserLivingInCity();
        allUsers[0] = UserLookupTestsUtils.getUserLivingInCity();
        allUsers[1] = UserLookupTestsUtils.getUserInCity();
        allUsers[2] = UserLookupTestsUtils.getUserNotLinkedToCity();
    }

    private void setupUsersForNoUsersResult(User[] allUsers, User[] usersLivingInCity) {
        allUsers[0] = UserLookupTestsUtils.getUserLivingInCity();
        allUsers[1] = UserLookupTestsUtils.getUserNotLinkedToCity();
    }

}
