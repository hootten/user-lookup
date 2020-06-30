package com.dwp.usersbycity.controller;

import com.dwp.usersbycity.models.User;
import com.dwp.usersbycity.service.UserLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserLookupController {

    private final UserLookupService userLookupService;

    @Autowired
    public UserLookupController(UserLookupService userLookupService) {
        this.userLookupService = userLookupService;
    }

    @GetMapping(Constants.USERS_LINKED_TO_CITY_ENDPOINT)
    public ResponseEntity<List<User>> getUsersLinkedToCity() {
        return this.userLookupService.getUsersLinkedToCity();
    }

}
