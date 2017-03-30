package de.heinemann.service;

import java.util.List;

import de.heinemann.domain.User;

public interface UserService {

	public User createUser(User user);
	
	public List<User> getUsers();
	
}
