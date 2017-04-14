package de.heinemann.aspect;

import org.junit.Ignore;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import de.heinemann.controller.ControllerTest;
import de.heinemann.domain.User;
import de.heinemann.security.Role;

@DatabaseTearDown("../reset.xml")
public class RequestLoggingTest extends ControllerTest {

	@Test
	@DatabaseSetup("requestlogging/logRequestWithoutException/prepared.xml")
	@ExpectedDatabase(value = "requestlogging/logRequestWithoutException/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void logRequestWithoutException() throws Exception {
		get("/users");
	}
	
	@Test
	@DatabaseSetup("requestlogging/logRequestWithUrlParameter/prepared.xml")
	@ExpectedDatabase(value = "requestlogging/logRequestWithUrlParameter/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void logRequestWithUrlParameter() throws Exception {
		get("/users/1/jobs");
	}		
	
	@Test
	@DatabaseSetup("requestlogging/logRequestWithUserAndContent/prepared.xml")
	@ExpectedDatabase(value = "requestlogging/logRequestWithUserAndContent/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void logRequestWithUserAndContent() throws Exception {
		String token = jwt.mail("pianoking@gmx.de").roles(Role.ROLE_ADMIN).build();

		User actualRequest = new User("user1@test.de");
				
		post(token, "/users", actualRequest);
	}
	
	@Ignore
	@Test
	@DatabaseSetup("requestlogging/logRequestWith403Exception/prepared.xml")
	@ExpectedDatabase(value = "requestlogging/logRequestWith403Exception/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void logRequestWith403Exception() throws Exception {
		String token = jwt.mail("pianoking@gmx.de").roles().build();
		
		User actualRequest = new User("user1@test.de");				
		
		post(token, "/users", actualRequest);
	}
	
}
