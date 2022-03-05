package com.example.ClientApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.ClientApp.config.ConfigUtils;
import com.example.ClientApp.model.AuthToken;
import com.example.ClientApp.model.CustomerRegistrationRequest;
import com.example.ClientApp.model.LoginRequest;

@RestController
@RequestMapping("/customer")
public class UserController {
	@Autowired
	private ConfigUtils  configUtils;
	
	private static RestTemplate restTemplate = new RestTemplate();

//	@Autowired
//    public UserController(RestTemplateBuilder builder) {
//        this.restTemplate = builder.build();
//    }
	
	@PostMapping("/register")
	public AuthToken register(@RequestBody CustomerRegistrationRequest user) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(HttpHeaders.AUTHORIZATION, configUtils.getBaseAuth());
		

        HttpEntity<CustomerRegistrationRequest> entity = new HttpEntity<CustomerRegistrationRequest>(user, headers);
        ResponseEntity<AuthToken> authToken=restTemplate.exchange(configUtils.getRegistrationUrl(), HttpMethod.POST, entity, AuthToken.class);
        System.out.println(authToken);
        
        return authToken.getBody();
	}
	
	@PostMapping("/login")
	public AuthToken login(@RequestBody LoginRequest loginDto) {
		 HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.add(HttpHeaders.AUTHORIZATION, configUtils.getBaseAuth());
	        
	        HttpEntity<Object> entity = new HttpEntity<Object>(loginDto, headers);
	        
	        ResponseEntity<AuthToken> authToken =restTemplate.exchange("http://localhost:8080/auth/user/login", HttpMethod.POST, entity, AuthToken.class);
	        return authToken.getBody();
	}
	
	@GetMapping("/wallet-balance")
	@ResponseStatus(HttpStatus.OK)
	public String balance() {
		return "200.22";
	}

}
