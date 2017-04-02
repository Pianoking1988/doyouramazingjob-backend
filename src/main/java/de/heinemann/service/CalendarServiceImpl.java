package de.heinemann.service;

import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class CalendarServiceImpl implements CalendarService {

	@Override
	public Calendar now() {
		return Calendar.getInstance();
	}

}
