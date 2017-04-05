package de.heinemann.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import de.heinemann.domain.User;
import de.heinemann.exception.ResourceConflictException;
import de.heinemann.security.Role;

public class UserControllerTest extends ControllerTest {

	@Test
	@DatabaseSetup("user/createUserWithNonExistingUserShouldCreateUser/prepared.xml")
	@ExpectedDatabase(value = "user/createUserWithNonExistingUserShouldCreateUser/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createUserWithNonExistingUserShouldCreateUser() throws Exception {
		String token = jwt.mail("pianoking@gmx.de").roles(Role.ROLE_ADMIN, Role.ROLE_USER).build();

		User actualRequest = new User("user1@test.de");
		User expectedResponse = new User(1, "user1@test.de", now(), "pianoking@gmx.de");
				
		expectContent(expectedResponse,
				post(token, "/users", actualRequest)
						.andExpect(status().isOk())
		);
	}

	@Test
	@DatabaseSetup("user/createUserWithExistingUserShouldThrow409/prepared.xml")
	@ExpectedDatabase(value = "user/createUserWithExistingUserShouldThrow409/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createUserWithExistingUserShouldThrow409() throws Exception {
		String token = jwt.mail("pianoking@gmx.de").roles(Role.ROLE_ADMIN, Role.ROLE_USER).build();
				
		User actualRequest = new User("user1@test.de");				
		ResourceConflictException expectedException = new ResourceConflictException("User with mail user1@test.de exists already");
		
		expectException(expectedException,
				post(token, "/users", actualRequest)
						.andExpect(status().isConflict())
		);
	}
	
}
