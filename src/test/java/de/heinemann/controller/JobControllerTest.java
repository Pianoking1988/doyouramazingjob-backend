package de.heinemann.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import de.heinemann.domain.Job;
import de.heinemann.domain.User;
import de.heinemann.exception.ResourceNotFoundException;
import de.heinemann.security.Role;

@DatabaseTearDown("../reset.xml")
public class JobControllerTest extends ControllerTest {

	@Test
	@DatabaseSetup("job/createJobWithExistingUserShouldCreateJob/prepared.xml")
	@ExpectedDatabase(value = "job/createJobWithExistingUserShouldCreateJob/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createJobWithExistingUserShouldCreateJob() throws Exception {
		String token = jwt.mail("user1@test.de").roles(Role.ROLE_USER).build();

		Job actualRequest = new Job("content");
		
		User user = new User(1, "user1@test.de", calendar("2017-04-01 00:30:59"), "pianoking@gmx.de");
		Job expectedResponse = new Job(1, user, "content", now(), "user1@test.de");
					
		expectContent(expectedResponse,
				post(token, "/users/1/jobs", actualRequest)
						.andExpect(status().isOk())
		);
	}
	
	@Test
	@DatabaseSetup("job/createJobWithNonExistingUserShouldThrow403/prepared.xml")
	@ExpectedDatabase(value = "job/createJobWithNonExistingUserShouldThrow403/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createJobWithNonExistingUserShouldThrow403() throws Exception {
		String token = jwt.mail("pianoking@gmx.de").roles(Role.ROLE_USER).build();
				
		Job actualRequest = new Job("content");
		ResourceNotFoundException expectedException = new ResourceNotFoundException("User with id 1 not found");
		
		expectException(expectedException,
				post(token, "/users/1/jobs", actualRequest)
						.andExpect(status().isNotFound()));
	}
	
	@Test
	@DatabaseSetup("job/createJobForAnotherUserShouldThrow403/prepared.xml")
	@ExpectedDatabase(value = "job/createJobForAnotherUserShouldThrow403/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createJobForAnotherUserShouldThrow403() throws Exception {
		String token = jwt.mail("user2@test.de").roles(Role.ROLE_USER).build();

		Job actualRequest = new Job("content");
							
		post(token, "/users/1/jobs", actualRequest)
				.andExpect(status().isForbidden());		
	}
	
	@Test
	public void createJobWithoutUserRoleShouldThrow403() throws Exception {
		String token = jwt.mail("pianoking@gmx.de").roles().build();
				
		Job actualRequest = new Job("content");
		
		post(token, "/users/1/jobs", actualRequest)
				.andExpect(status().isForbidden());
	}
	
	@Test
	@DatabaseSetup("job/createJobShouldTrimJobsIfExceeding/prepared.xml")
	@ExpectedDatabase(value = "job/createJobShouldTrimJobsIfExceeding/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createJobShouldTrimJobsIfExceeding() throws Exception {
		String token = jwt.mail("user1@test.de").roles(Role.ROLE_USER).build();

		Job actualRequest = new Job("content4");
		
		User user = new User(1, "user1@test.de", calendar("2017-04-01 00:30:59"), "pianoking@gmx.de");
		Job expectedResponse = new Job(5, user, "content4", now(), "user1@test.de");
					
		expectContent(expectedResponse,
				post(token, "/users/1/jobs", actualRequest)
						.andExpect(status().isOk())
		);
	}
	
	@Test
	@DatabaseSetup("job/getJobsForOneUserShouldReturnJobsOfThisUser/prepared.xml")
	@ExpectedDatabase(value = "job/getJobsForOneUserShouldReturnJobsOfThisUser/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void getJobsForOneUserShouldReturnJobsOfThisUser() throws Exception {
		User user = new User(1, "user1@test.de", calendar("2017-04-01 00:30:59"), "pianoking@gmx.de");

		List<Job> expectedResponse = Arrays.asList(
				new Job(2, user, "content2", calendar("2017-04-01 02:30:59"), "user1@test.de"),
				new Job(1, user, "content1", calendar("2017-04-01 01:30:59"), "user1@test.de")
		);
				
		expectContent(expectedResponse,
				get("/users/1/jobs")
						.andExpect(status().isOk())
		);
	}	
	
}
