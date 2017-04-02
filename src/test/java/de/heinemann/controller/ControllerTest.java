package de.heinemann.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.heinemann.Application;
import de.heinemann.builder.JWT;
import de.heinemann.config.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={Application.class, TestConfiguration.class})
public class ControllerTest {

	protected MockMvc mockMvc;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
		
	@Autowired
	protected JWT jwt;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
	}
	
	protected ResultActions post(String path, Object content, String token) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders.post(path)
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(content)));
	}

}
