package de.heinemann.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.heinemann.domain.Job;
import de.heinemann.domain.User;
import de.heinemann.repository.JobRepository;

@Service
public class JobServiceImpl implements JobService {

	@Value("${job.number.per.user}")
	private int numberOfJobsPerUser;
	
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
		job = jobRepository.save(job);
		trimJobsOfUserIfNecessary(user);
		return job;
	}

	@Override
	public List<Job> getJobs(User user) {
		return jobRepository.findByUserOrderByCreatedDesc(user);
	}
	
	private void trimJobsOfUserIfNecessary(User user) {
		List<Job> jobs = getJobs(user);
		while (jobs.size() > numberOfJobsPerUser) {
			Job earliestJob = jobs.remove(jobs.size() - 1);
			jobRepository.delete(earliestJob);
		}
	}

}
