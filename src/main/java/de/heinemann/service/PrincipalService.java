package de.heinemann.service;

import java.util.Collection;

import com.auth0.spring.security.api.Auth0UserDetails;

public interface PrincipalService {

	public Auth0UserDetails getPrincipal();
	
	public String getUsername();
	
	public Collection<Object> getHobbies();
	
}
