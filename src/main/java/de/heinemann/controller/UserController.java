package de.heinemann.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.heinemann.domain.User;
import de.heinemann.service.UserService;

/**
 * Everybody can get all users.
 * Users with role USER can create themselves in the user table.
 * Users with role ADMIN can create any other user.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public List<User> getUsers() {
        return userService.getUsers();
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@RequestMapping(method = RequestMethod.POST)
	public User createUser(@RequestBody User user) {
		userService.assertThatUserIsNotExisting(user);
		User result = userService.createUser(user);
        return result;
	}
	
}
