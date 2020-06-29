package com.dwp.usersbycity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "external-api")
public class ExternalApiProperties {

    public final String baseUrl;
    public final String usersLivingInCityEndpoint;
    public final String allUsersEndpoint;

    public ExternalApiProperties(String baseUrl, String usersLivingInCityEndpoint, String allUsersEndpoint) {
        this.baseUrl = baseUrl;
        this.usersLivingInCityEndpoint = usersLivingInCityEndpoint;
        this.allUsersEndpoint = allUsersEndpoint;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsersLivingInCityEndpoint() {
        return usersLivingInCityEndpoint;
    }

    public String getAllUsersEndpoint() {
        return allUsersEndpoint;
    }
}
