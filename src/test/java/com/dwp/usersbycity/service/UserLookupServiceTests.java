package com.dwp.usersbycity.service;

import com.dwp.usersbycity.config.ExternalApiProperties;
import com.dwp.usersbycity.config.GeoProperties;
import com.dwp.usersbycity.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = UserLookupService.class)
//@Import(TestConfig.class)
//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserLookupServiceTests {

    @InjectMocks
    UserLookupService userLookupService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GeoProperties geoProperties;

    @Mock
    private ExternalApiProperties externalApiProperties;

    @Mock
    List<User> cityUsers;

    MockRestServiceServer mockServer;

    ObjectMapper mapper = new ObjectMapper();

    User[] users = new User[1];

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        User user = new User();
        user.setId("1");
        users[0] = user;

    }

    @Test
    public void testGetUsersLinkedToCity() throws JsonProcessingException {
        when(externalApiProperties.getBaseUrl()).thenReturn("BASE");
        when(externalApiProperties.getUsersLivingInCityEndpoint()).thenReturn("/LIVING_IN_CITY");
        when(externalApiProperties.getAllUsersEndpoint()).thenReturn("/ALL_USERS");
        when(restTemplate.getForEntity(anyString(), eq(User[].class)))
                .thenReturn(new ResponseEntity<>(users, HttpStatus.OK));
//        mockServer.expect(ExpectedCount.once(),
//                requestTo("BASE/LIVING_IN_CITY"))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(users))
//                );
//        Mockito.when(restTemplate.getForEntity("BASE/LIVING_IN_CITY", User[].class))
//                .thenReturn(new ResponseEntity<User[]>(users, HttpStatus.OK));

        userLookupService.getUsersLinkedToCity();

    }
}
