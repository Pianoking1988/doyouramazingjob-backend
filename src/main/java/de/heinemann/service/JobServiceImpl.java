package de.heinemann.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.heinemann.domain.Job;
import de.heinemann.domain.User;
import de.heinemann.repository.JobRepository;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private PrincipalService principalService;

	@Autowired
	private CalendarService timeService;
	
	@Override
	public Job createJob(Job job, User user) {
		job.setId(0);
		job.setUser(user);
		job.setCreated(timeService.now());
		job.setCreatedBy(principalService.getUsername());
		// TODO trim number of saved jobs according to environment variable
		return jobRepository.save(job);
	}

	@Override
	public List<Job> getJobs(User user) {
		return jobRepository.findByUserOrderByCreatedDesc(user);
	}

}
