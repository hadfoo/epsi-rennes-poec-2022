package fr.epsi.orel.controller;

import fr.epsi.orel.domain.User;
import fr.epsi.orel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/public/register")
    public void addUser(User user) {
        userService.addUser(user);
    }
}
