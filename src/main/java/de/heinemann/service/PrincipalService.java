package de.heinemann;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.auth0.spring.security.api.Auth0UserDetails;

/**
 * Extracts information from user jwt token.
 * Information like "hobbies" have to be included into jwt token via 
 * "scope" parameter during login.
 */
@Component
public class PrincipalService {

	public Auth0UserDetails getPrincipal() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Auth0UserDetails) authentication.getPrincipal();
	}
	
	public String getUsername() {
		return getPrincipal().getUsername();
	}
	
	public Collection<Object> getHobbies() {
		return (Collection<Object>) getPrincipal().getAuth0Attribute("hobbies");
	}
	
}
