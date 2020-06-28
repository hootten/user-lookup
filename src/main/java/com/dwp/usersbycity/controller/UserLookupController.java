package com.dwp.usersbycity.controller;

import com.dwp.usersbycity.models.User;
import com.dwp.usersbycity.service.UserLookupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserLookupController {

    private final UserLookupService userLookupService;

    public UserLookupController(UserLookupService userLookupService) {
        this.userLookupService = userLookupService;
    }

    @GetMapping(Constants.usersByCityEndpoint)
    public ResponseEntity<List<User>> getUsersByCity() throws Exception {
        return this.userLookupService.getUsersLinkedToCity();
    }

}
