package de.heinemann.service;

import java.util.List;

import de.heinemann.domain.Job;
import de.heinemann.domain.User;

public interface JobService {

	public Job createJob(Job job, User user);
	
	public List<Job> getJobs(User user);
	
}
