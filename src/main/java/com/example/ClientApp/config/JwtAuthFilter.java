package com.example.ClientApp.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

public class JwtAuthFilter extends AbstractAuthenticationProcessingFilter{

	protected JwtAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);

	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		try {
			String token = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (Strings.isEmpty(token)) {
				throw new RuntimeException("No Jwt token found in headers");
				
			}
			UserNamePasswordAuthTokenImpl auth = new UserNamePasswordAuthTokenImpl(token);
			return getAuthenticationManager().authenticate(auth);
			
		} catch (Exception e) {
			response.sendError(response.SC_UNAUTHORIZED,e.getMessage());
			return null;
		}
		
	}
	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

}
