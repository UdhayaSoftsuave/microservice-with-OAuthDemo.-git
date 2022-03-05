package com.example.ClientApp.model;


import lombok.Builder;

public class AuthToken {
	
	private String token ;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}