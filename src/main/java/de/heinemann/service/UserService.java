package de.heinemann.service;

import java.util.List;

import de.heinemann.domain.User;

public interface UserService {

	public User createUser(User user);
	
	public User getUser(long id);
	
	public List<User> getUsers();
	
	public void assertThatUserIsNotExisting(User user);
	
}
