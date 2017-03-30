package de.heinemann.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.heinemann.domain.User;
import de.heinemann.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private UserService userService;
		
	@RequestMapping(method = RequestMethod.GET)
	public List<User> getUsers() {
        return userService.getUsers();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public User createUser(@RequestBody User user) {
        return userService.createUser(user);
	}
	
}
