package de.heinemann.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.heinemann.domain.Job;
import de.heinemann.domain.User;
import de.heinemann.exception.ForbiddenException;
import de.heinemann.service.JobService;
import de.heinemann.service.PrincipalService;
import de.heinemann.service.UserService;

@RestController
@RequestMapping(value = "/users/{userId}/jobs")
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PrincipalService principalService;
		
	@RequestMapping(method = RequestMethod.GET)
	public List<Job> getJobs(@PathVariable long userId) {
		User user = userService.getUser(userId);
        return jobService.getJobs(user);
	}
	
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(method = RequestMethod.POST)
	public Job createJob(@PathVariable long userId, @RequestBody Job job) {
		User user = userService.getUser(userId);
		
		String principalUsername = principalService.getUsername(); 
		if (!principalUsername.equalsIgnoreCase(user.getMail())) {
			throw new ForbiddenException("User " + principalUsername + " is not allowed to create a job for user " + user.getMail());
		}

        return jobService.createJob(job, user);
	}
	
}
