package de.heinemann;

import org.springframework.stereotype.Component;

import com.auth0.Auth0;
import com.auth0.authentication.AuthenticationAPIClient;
import com.auth0.authentication.result.UserProfile;
import com.auth0.request.Request;
import com.auth0.spring.security.api.Auth0JWTToken;

/**
 * Client to communicate with Auth0.
 * 
 * Is only necessary if user information have not been included into the jwt token.
 */
@Component
public class Auth0Client {

    private final String clientid;
    private final String domain;
    private final Auth0 auth0;
    private final AuthenticationAPIClient client;

    public Auth0Client(String clientid, String domain) {
        this.clientid = clientid;
        this.domain = domain;
        this.auth0 = new Auth0(clientid, domain);
        this.client = this.auth0.newAuthenticationAPIClient();
    }

    public String getUsername(Auth0JWTToken token) {
        final Request<UserProfile> request = client.tokenInfo(token.getJwt());
        final UserProfile profile = request.execute();
        return profile.getEmail();
    }

}