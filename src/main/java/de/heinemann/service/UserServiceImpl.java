package de.heinemann.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.heinemann.domain.User;
import de.heinemann.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PrincipalService principalService;

	@Autowired
	private TimeService timeService;
	
	@Override
	public User createUser(User user) {
		user.setId(0);
		user.setCreated(timeService.now());
		user.setCreatedBy(principalService.getUsername());
		return userRepository.save(user);
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

}