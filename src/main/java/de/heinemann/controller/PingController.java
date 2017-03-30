package de.heinemann.controller;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth0.spring.security.api.Auth0JWTToken;

import de.heinemann.service.PrincipalService;

@Controller
@Component
public class PingController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PrincipalService principalService;
	
	@RequestMapping(value = "/ping")
	@ResponseBody
	public String ping() {		
		return "All good. You DO NOT need to be authenticated to call /ping.";
	}

	@RequestMapping(value = "/secured/ping")
	@ResponseBody
	public String securedPing(final Principal principal) {
        printGrantedAuthorities((Auth0JWTToken) principal);
		return "All good, " + principalService.getUsername() + ". You DO need to be authenticated to call /secured/ping";
	}
	
	@RequestMapping(value = "/admin/ping")
	@ResponseBody
	public String adminPing(final Principal principal) {        
		return "All good, " + principalService.getUsername() + ". You DO need to be authenticated & admin to call /admin/ping";
	}

	@RequestMapping(value = "/user/ping")
	@ResponseBody
	public String userPing(final Principal principal) {        		
		return "All good, " + principalService.getUsername() + ". You DO need to be authenticated & user to call /user/ping"
				+ ". Your hobbies are " + StringUtils.join(principalService.getHobbies(), " and ");
	}

	@RequestMapping(value = "/missingRole/ping")
	@ResponseBody
	public String missingRolePing(final Principal principal) {   
		return "All good, " + principalService.getUsername() + ". You DO need to be authenticated & missingRole to call /missingRole/ping";
	}
	
	private void printGrantedAuthorities(final Auth0JWTToken principal) {
		if (principal.getAuthorities().isEmpty()) {
			logger.info("No authorities");
		}
		
		for(final GrantedAuthority grantedAuthority: principal.getAuthorities()) {
		    final String authority = grantedAuthority.getAuthority();
		    logger.info("Authority: {}", authority);
		}				
	}
	 
}
