package de.heinemann.service;

import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class TimeServiceImpl implements TimeService {

	@Override
	public Calendar now() {
		return Calendar.getInstance();
	}

}
