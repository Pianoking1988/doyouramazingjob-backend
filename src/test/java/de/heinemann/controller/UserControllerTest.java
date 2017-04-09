package de.heinemann.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import de.heinemann.domain.User;
import de.heinemann.exception.ForbiddenException;
import de.heinemann.exception.ResourceConflictException;
import de.heinemann.security.Role;

@DatabaseTearDown("../reset.xml")
public class UserControllerTest extends ControllerTest {
	
	@Test
	@DatabaseSetup("user/createUserWithNonExistingUserWithRoleAdminShouldCreateUser/prepared.xml")
	@ExpectedDatabase(value = "user/createUserWithNonExistingUserWithRoleAdminShouldCreateUser/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createUserWithNonExistingUserWithRoleAdminShouldCreateUser() throws Exception {
		String token = jwt.mail("pianoking@gmx.de").roles(Role.ROLE_ADMIN).build();

		User actualRequest = new User("user1@test.de");
		User expectedResponse = new User(1, "user1@test.de", now(), "pianoking@gmx.de");
				
		expectContent(expectedResponse,
				post(token, "/users", actualRequest)
						.andExpect(status().isOk())
		);
	}

	@Test
	@DatabaseSetup("user/createUserWithExistingUserWithRoleAdminShouldThrow409/prepared.xml")
	@ExpectedDatabase(value = "user/createUserWithExistingUserWithRoleAdminShouldThrow409/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createUserWithExistingUserWithRoleAdminShouldThrow409() throws Exception {
		String token = jwt.mail("pianoking@gmx.de").roles(Role.ROLE_ADMIN).build();
				
		User actualRequest = new User("user1@test.de");				
		ResourceConflictException expectedException = new ResourceConflictException("User with mail user1@test.de exists already");
		
		expectException(expectedException,
				post(token, "/users", actualRequest)
						.andExpect(status().isConflict())
		);
	}
	
	@Test
	@DatabaseSetup("user/createUserWithNonExistingUserWithRoleUserShouldCreateUser/prepared.xml")
	@ExpectedDatabase(value = "user/createUserWithNonExistingUserWithRoleUserShouldCreateUser/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createUserWithNonExistingUserWithRoleUserShouldCreateUser() throws Exception {
		String token = jwt.mail("user1@test.de").roles(Role.ROLE_USER).build();

		User actualRequest = new User("user1@test.de");
		User expectedResponse = new User(1, "user1@test.de", now(), "user1@test.de");
				
		expectContent(expectedResponse,
				post(token, "/users", actualRequest)
						.andExpect(status().isOk())
		);
	}
	
	@Test
	@DatabaseSetup("user/createUserWithExistingUserWithRoleUserShouldThrow409/prepared.xml")
	@ExpectedDatabase(value = "user/createUserWithExistingUserWithRoleUserShouldThrow409/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createUserWithExistingUserWithRoleUserShouldThrow409() throws Exception {
		String token = jwt.mail("user1@test.de").roles(Role.ROLE_USER).build();

		User actualRequest = new User("user1@test.de");				
		ResourceConflictException expectedException = new ResourceConflictException("User with mail user1@test.de exists already");
		
		expectException(expectedException,
				post(token, "/users", actualRequest)
						.andExpect(status().isConflict())
		);
	}
	
	@Test
	@DatabaseSetup("user/createUserWithNonExistingDifferentUserWithRoleUserShouldThrow403/prepared.xml")
	@ExpectedDatabase(value = "user/createUserWithNonExistingDifferentUserWithRoleUserShouldThrow403/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createUserWithNonExistingDifferentUserWithRoleUserShouldThrow403() throws Exception {
		String token = jwt.mail("user2@test.de").roles(Role.ROLE_USER).build();

		User actualRequest = new User("user1@test.de");				
		ForbiddenException expectedException = new ForbiddenException("User with mail user1@test.de cannot be created by user user2@test.de");
		
		expectException(expectedException,
				post(token, "/users", actualRequest)
						.andExpect(status().isForbidden())
		);
	}
	
	@Test
	public void createUserWithNonExistingUserWithoutAnyRoleShouldThrow403() throws Exception {
		String token = jwt.mail("pianoking@gmx.de").roles().build();
				
		User actualRequest = new User("user1@test.de");				
		
		post(token, "/users", actualRequest)
				.andExpect(status().isForbidden());
	}
	
	@Test
	@DatabaseSetup("user/getUsersShouldReturnUsers/prepared.xml")
	@ExpectedDatabase(value = "user/getUsersShouldReturnUsers/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void getUsersShouldReturnUsers() throws Exception {
		List<User> expectedResponse = Arrays.asList(
				new User(1, "user1@test.de", calendar("2017-04-01 00:30:59"), "pianoking@gmx.de"),
				new User(2, "user2@test.de", calendar("2017-04-01 01:30:59"), "developer@gmx.de")
		);
				
		expectContent(expectedResponse,
				get("/users")
						.andExpect(status().isOk())
		);
	}
	
}
