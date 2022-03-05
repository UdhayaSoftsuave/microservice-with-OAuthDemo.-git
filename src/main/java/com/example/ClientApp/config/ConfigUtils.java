package com.example.ClientApp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.auth.service")
public class ConfigUtils {
	private String registrationUrl;
	private String validateUrl;
	private String loginUrl;
	private String baseAuth;
	

}
