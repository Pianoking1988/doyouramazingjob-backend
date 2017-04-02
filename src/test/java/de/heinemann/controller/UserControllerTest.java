package de.heinemann.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import de.heinemann.domain.User;
import de.heinemann.security.Role;

@DatabaseTearDown("reset.xml")
public class UserControllerTest extends ControllerTest {

	@Test
	@DatabaseSetup("prepared.xml")
	@ExpectedDatabase(value = "expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void createUserWithNonExistingUser() throws Exception {
		String token = jwt.mail("pianoking@gmx.de").roles(Role.ROLE_ADMIN, Role.ROLE_USER).build();
		
		User user = new User();
		user.setMail("user1@test.de");
		
		post("/users", user, token)
			.andExpect(status().is2xxSuccessful());
			//.andExpect(content().json("[]"));
	}

	@Test
	public void testGet() throws Exception {
		mockMvc.perform(get("/users"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().json("[]"));
	}
	
	@Test
	public void testPing() throws Exception {
		mockMvc.perform(get("/ping"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().json("[]"));
	}

}
