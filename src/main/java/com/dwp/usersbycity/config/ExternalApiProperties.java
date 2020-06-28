package com.dwp.usersbycity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "external-api")
public class ExternalApiProperties {

    public final String baseUrl;
    public final String usersInCityEndpoint;
    public final String allUsersEndpoint;

    public ExternalApiProperties(String baseUrl, String usersInCityEndpoint, String allUsersEndpoint) {
        this.baseUrl = baseUrl;
        this.usersInCityEndpoint = usersInCityEndpoint;
        this.allUsersEndpoint = allUsersEndpoint;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsersInCityEndpoint() {
        return usersInCityEndpoint;
    }

    public String getAllUsersEndpoint() {
        return allUsersEndpoint;
    }
}
