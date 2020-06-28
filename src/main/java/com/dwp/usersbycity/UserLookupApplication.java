package com.dwp.usersbycity;

import com.dwp.usersbycity.config.ExternalApiProperties;
import com.dwp.usersbycity.config.GeoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({GeoProperties.class, ExternalApiProperties.class})
public class UserLookupApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserLookupApplication.class, args);
	}

}
