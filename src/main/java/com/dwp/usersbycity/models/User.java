package com.dwp.usersbycity.models;

import com.dwp.usersbycity.controller.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String ipAddress;
    private double latitude;
    private double longitude;

    public int getId() {
        return id;
    }

    public void setId(String id) {
        try {
            this.id = this.id != Constants.ERROR_ID ? Integer.parseInt(id) : Constants.ERROR_ID;
        } catch (NumberFormatException e) {
            this.id = Constants.ERROR_ID;
        }
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("ip_address")
    public String getIpAddress() {
        return ipAddress;
    }

    @JsonProperty("ip_address")
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        try {
            this.latitude = Double.parseDouble(latitude);
        } catch (NumberFormatException e) {
            this.id = Constants.ERROR_ID;
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        try {
            this.longitude = Double.parseDouble(longitude);
        } catch (NumberFormatException e) {
            this.id = Constants.ERROR_ID;
        }
    }

}
