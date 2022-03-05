package com.example.ClientApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JwtAuthProvider authProvider;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/customer/register","/customer/login");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		OrRequestMatcher orRequestMatcher = new OrRequestMatcher(new AntPathRequestMatcher("/customer/**"));
		JwtAuthFilter authFilter = new JwtAuthFilter(orRequestMatcher);
		authFilter.setAuthenticationManager(authenticationManager());
		authFilter.setAuthenticationSuccessHandler(new SuccessHandler());
		
		
	 http.csrf().disable()
		.formLogin().disable()
		.logout().disable()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authenticationProvider(authProvider)
		.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	

}
