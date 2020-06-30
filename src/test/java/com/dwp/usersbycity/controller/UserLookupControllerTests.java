package com.dwp.usersbycity.controller;

import com.dwp.usersbycity.utils.UserLookupTestsUtils;
import com.dwp.usersbycity.models.User;
import com.dwp.usersbycity.service.UserLookupService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserLookupController.class)
public class UserLookupControllerTests {

    @InjectMocks
    private UserLookupController userLookupController;

    @Mock
    private UserLookupService userLookupService;

    private MockMvc mockMvc;

    private final List<User> usersLinkedToCity = new ArrayList<>();

    @Before
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userLookupController)
                .build();
    }

    @Test
    public void testGetUsersLinkedToCity() throws Exception {
        usersLinkedToCity.add(UserLookupTestsUtils.getUserLivingInCity());
        usersLinkedToCity.add(UserLookupTestsUtils.getUserInCity());
        when(userLookupService.getUsersLinkedToCity())
                .thenReturn(new ResponseEntity<>(usersLinkedToCity, HttpStatus.OK));

        mockMvc.perform(get("/usersLinkedToCity"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2]").doesNotExist());

        verify(userLookupService, times(1)).getUsersLinkedToCity();
        verifyNoMoreInteractions(userLookupService);
    }

    @Test
    public void testGetNoUsersLinkedToCity() throws Exception {
        when(userLookupService.getUsersLinkedToCity())
                .thenReturn(new ResponseEntity<>(usersLinkedToCity, HttpStatus.OK));

        mockMvc.perform(get("/usersLinkedToCity"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0]").doesNotExist());

        verify(userLookupService, times(1)).getUsersLinkedToCity();
        verifyNoMoreInteractions(userLookupService);
    }

}
