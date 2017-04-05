package de.heinemann.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.heinemann.domain.User;
import de.heinemann.exception.ResourceConflictException;
import de.heinemann.exception.ResourceNotFoundException;
import de.heinemann.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PrincipalService principalService;

	@Autowired
	private CalendarService timeService;
	
	@Override
	public User createUser(User user) {
		user.setId(0);
		user.setCreated(timeService.now());
		user.setCreatedBy(principalService.getUsername());
		return userRepository.save(user);
	}

	@Override
	public User getUser(long id) throws ResourceNotFoundException {
		User user = userRepository.findOne(id);
		if (user == null) {
			throw new ResourceNotFoundException("User with id " + id + " not found");
		} else {
			return user;
		}
	}
	
	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	public void assertThatUserIsNotExisting(User user) {
		User existingUser = userRepository.findByMailIgnoreCase(user.getMail());
		if (existingUser != null) {
			throw new ResourceConflictException("User with mail " + user.getMail() + " exists already");
		}		
	}

}
