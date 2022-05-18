package fr.epsi.rennes.poec.hadf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.rennes.poec.hadf.domain.User;
import fr.epsi.rennes.poec.hadf.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/public/register")
	public void addUser(User user) {
		userService.addUser(user);
	}

}
