package com.example.ClientApp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.ClientApp.model.UserDetailsImpl;

import lombok.SneakyThrows;

@Component
public class JwtAuthProvider extends AbstractUserDetailsAuthenticationProvider{

	@Autowired
	private ConfigUtils configUtils;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@SneakyThrows
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		try {
			UserNamePasswordAuthTokenImpl authTokenImpl = (UserNamePasswordAuthTokenImpl) authentication;
			
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.add("auth",authTokenImpl.getToken());
			headers.add(HttpHeaders.AUTHORIZATION,configUtils.getBaseAuth());
			
			HttpEntity<UserNamePasswordAuthTokenImpl> httpEntity = new HttpEntity<>(headers);
			ValidateResponse response = new RestTemplate().exchange(configUtils.getValidateUrl(), HttpMethod.GET, httpEntity,
														ValidateResponse.class).getBody();
			
			List<GrantedAuthority> grantedAuthorities =
                    AuthorityUtils.commaSeparatedStringToAuthorityList(response.getRoles());
		
			return new UserDetailsImpl(response.getUserName(), response.getUserId(),
					grantedAuthorities);
			
		} catch (Exception e) {
			throw new RuntimeException("Invalid Axcess Token");
		}
	}
	 @Override
	    public boolean supports(Class<?> authentication) {
	        return UserNamePasswordAuthTokenImpl.class.isAssignableFrom(authentication);
	    }

}
