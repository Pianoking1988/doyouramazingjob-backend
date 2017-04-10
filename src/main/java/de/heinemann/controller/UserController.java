package de.heinemann.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.heinemann.domain.User;
import de.heinemann.exception.ForbiddenException;
import de.heinemann.security.Role;
import de.heinemann.service.PrincipalService;
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
	
	@Autowired
	private PrincipalService principalService;

	@RequestMapping(method = RequestMethod.GET)
	public List<User> getUsers() {
        return userService.getUsers();
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@RequestMapping(method = RequestMethod.POST)
	public User createUser(@RequestBody User user) {
		userService.assertThatUserIsNotExisting(user);
		
		String username = principalService.getUsername();
		if (principalService.hasRole(Role.ROLE_USER)
				&& !username.equalsIgnoreCase(user.getMail())) {
			throw new ForbiddenException("User with mail " + user.getMail() + " cannot be created by user " + username);
		}
		
		User result = userService.createUser(user);
        return result;
	}
	
}
