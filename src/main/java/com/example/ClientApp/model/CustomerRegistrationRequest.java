package com.example.ClientApp.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerRegistrationRequest {

	private String userName;
	private String email;
	private String mobile;
	private String address;
	private String password;

}
