package de.heinemann.builder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.heinemann.security.Role;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWT {

	private JwtBuilder jwtBuilder = Jwts.builder();
	
	@Value("${auth0.signingAlgorithm}")
	private String signingAlgorithm;
		
	@Value("${auth0.clientSecret}")
	private String secret;
			
	@Value("${auth0.issuer}")
	private String issuer;
	
	@Value("${auth0.clientId}")
	private String audience;
	
	public JWT mail(String mail) {
		jwtBuilder.claim("email", mail);
		return this;
	}
	
	public JWT roles(Role... roles) {
		if (roles == null || roles.length == 0) {
			return this;
		}
		
		List<String> roleStrings = new ArrayList<>();
		for (Role role : roles) {
			roleStrings.add(role.toString());
		}

		jwtBuilder.claim("roles", roleStrings);
		
		return this;
	}
	
	public String build() throws UnsupportedEncodingException {
		SignatureAlgorithm algorithm = SignatureAlgorithm.forName(signingAlgorithm);
		return jwtBuilder
				.setHeaderParam("typ", "JWT")
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 60000))
				.setIssuer(issuer)
				.setAudience(audience)
				.signWith(algorithm, secret.getBytes("UTF-8"))
				.compact();
	}
	
}
