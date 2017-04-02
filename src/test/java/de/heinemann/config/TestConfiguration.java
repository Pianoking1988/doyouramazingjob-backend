package de.heinemann.config;

import java.util.Calendar;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import de.heinemann.service.CalendarService;

@Configuration
public class TestConfiguration {

	/**
	 * Returns a calendar service that returns always April 1, 2017 at 12:30:59 CEST.
	 */
	@Primary
	@Bean
	public CalendarService getCalendarService() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, 03, 01, 12, 30, 59);
		
		CalendarService calendarService = Mockito.mock(CalendarService.class);
		Mockito.when(calendarService.now()).thenReturn(calendar);
		return calendarService;
	}
	
}
