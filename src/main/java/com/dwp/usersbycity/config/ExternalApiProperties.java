package com.dwp.usersbycity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "external-api")
public class ExternalApiProperties {

    public final String BASE_URL;
    public final String USERS_LIVING_IN_CITY_ENDPOINT;
    public final String ALL_USERS_ENDPOINT;

    public ExternalApiProperties(String baseUrl, String usersLivingInCityEndpoint, String allUsersEndpoint) {
        this.BASE_URL = baseUrl;
        this.USERS_LIVING_IN_CITY_ENDPOINT = usersLivingInCityEndpoint;
        this.ALL_USERS_ENDPOINT = allUsersEndpoint;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }

    public String getUsersLivingInCityEndpoint() {
        return USERS_LIVING_IN_CITY_ENDPOINT;
    }

    public String getAllUsersEndpoint() {
        return ALL_USERS_ENDPOINT;
    }

}
