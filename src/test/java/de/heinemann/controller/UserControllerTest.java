package de.heinemann.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import de.heinemann.builder.TestException;
import de.heinemann.domain.User;
import de.heinemann.exception.ResourceNotFoundException;
import de.heinemann.security.Role;

//@DatabaseTearDown("../reset.xml")
public class UserControllerTest extends ControllerTest {

	@Test
	@DatabaseSetup("user/createUserWithNonExistingUserShouldCreateUser/prepared.xml")
	@ExpectedDatabase(value = "user/createUserWithNonExistingUserShouldCreateUser/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createUserWithNonExistingUserShouldCreateUser() throws Exception {
		String token = jwt.mail("pianoking@gmx.de").roles(Role.ROLE_ADMIN, Role.ROLE_USER).build();

		User actualRequest = new User("user1@test.de");
		User expectedResponse = new User(1, "user1@test.de", now(), "pianoking@gmx.de");
				
		post(token, "/users", actualRequest)
			.andExpect(status().isOk())
			.andExpect(content().json(json(expectedResponse), true));
	}

	@Test
	@DatabaseSetup("user/createUserWithExistingUserShouldThrow409/prepared.xml")
	@ExpectedDatabase(value = "user/createUserWithExistingUserShouldThrow409/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createUserWithExistingUserShouldThrow409() throws Exception {
		String token = jwt.mail("pianoking@gmx.de").roles(Role.ROLE_ADMIN, Role.ROLE_USER).build();
				
		User actualRequest = new User("user1@test.de");
		TestException expectedException = new TestException(ResourceNotFoundException.class, "User with id 3 not found");
				
		post(token, "/users", actualRequest)
			.andExpect(status().isConflict())
			.andExpect(content().json(json(expectedException), false));
	}
	
}
