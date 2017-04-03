package de.heinemann.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.Calendar;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

import de.heinemann.Application;
import de.heinemann.builder.JWT;
import de.heinemann.config.TestConfiguration;
import de.heinemann.service.CalendarService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={Application.class, TestConfiguration.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
public class ControllerTest {

	protected MockMvc mockMvc;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
		
	@Autowired
	protected JWT jwt;
	
	@Autowired
	protected CalendarService calendarService;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
	}
			
	protected Calendar now() {
		return calendarService.now();
	}
	
	protected String json(Object value) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(value);
	}
	
	protected ResultActions post(String token, String path, Object requestContent) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders.post(path)
				.header("Authorization", "Bearer " + token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(requestContent)))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));				
	}

}
