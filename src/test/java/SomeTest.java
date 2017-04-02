import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.heinemann.Application;
import de.heinemann.builder.JWT;
import de.heinemann.config.TestConfiguration;
import de.heinemann.controller.PingController;
import de.heinemann.controller.UserController;
import de.heinemann.domain.User;
import de.heinemann.security.Role;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes=Application.class)
@SpringBootTest(classes={Application.class, TestConfiguration.class})
public class SomeTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private PingController pingController;
	
	@Autowired
	private JWT jwt;
	
	@Before
	public void init() {
//		mockMvc = MockMvcBuilders
//				.standaloneSetup(userController, pingController)
//				.build();

		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}
	
	@Test
	public void testPost() throws Exception {
		try {
			String token = jwt.mail("pianoking@gmx.de").roles(Role.ROLE_ADMIN, Role.ROLE_USER).build();
			
			User user = new User();
			user.setMail("olli@test.de");
			
			String json = new ObjectMapper().writeValueAsString(user);
			
			mockMvc.perform(post("/users")
					.header("Authorization", "Bearer " + token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().json("[]"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
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
