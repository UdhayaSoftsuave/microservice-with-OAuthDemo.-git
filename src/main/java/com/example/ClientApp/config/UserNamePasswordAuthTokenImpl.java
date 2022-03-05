package com.example.ClientApp.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNamePasswordAuthTokenImpl extends UsernamePasswordAuthenticationToken {
	
	private String token;

	public UserNamePasswordAuthTokenImpl(String token) {
		super(null, null);
		this.token = token;
	}	
}
