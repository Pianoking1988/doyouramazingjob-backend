package de.heinemann.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.auth0.spring.security.api.Auth0SecurityConfig;

import de.heinemann.client.Auth0Client;
import de.heinemann.service.PrincipalService;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationConfig extends Auth0SecurityConfig {

	/**
	 * Provides Auth0 API access. Only necessary to communicate directly with Auth0.
	 * Information from jwt token can be extracted without this client via {@link PrincipalService}.
	 */
	@Bean
	public Auth0Client auth0Client() {
		return new Auth0Client(clientId, issuer);
	}

	/**
	 * Our API Configuration - for Profile CRUD operations
	 *
	 * Here we choose not to bother using the `auth0.securedRoute` property
	 * configuration and instead ensure any unlisted endpoint in our config is
	 * secured by default
	 */
	@Override
	protected void authorizeRequests(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/ping").permitAll()
				.antMatchers(HttpMethod.GET, "/users").permitAll()
				.antMatchers(HttpMethod.GET, "/users/*/jobs").permitAll()
				.antMatchers("/missinguser/**").hasAnyAuthority("ROLE_USER")
				.antMatchers("/missingRole/**").hasAnyAuthority("ROLE_MISSING")
				.anyRequest().hasAnyAuthority("ROLE_ADMIN");
	}

}
