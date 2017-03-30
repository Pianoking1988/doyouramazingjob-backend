package de.heinemann.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.heinemann.domain.Job;
import de.heinemann.domain.User;
import de.heinemann.service.JobService;
import de.heinemann.service.UserService;

@RestController
@RequestMapping(value = "/users/{userId}/jobs")
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private UserService userService;
		
	@RequestMapping(method = RequestMethod.GET)
	public List<Job> getJobs(@PathVariable long userId) {
		User user = userService.getUser(userId);
        return jobService.getJobs(user);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Job createJob(@PathVariable long userId, @RequestBody Job job) {
		User user = userService.getUser(userId);
		// TODO Assert that users can only create jobs for themselves
        return jobService.createJob(job, user);
	}
	
}
