package fr.rennes.epsi.poec.bob.guitarshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/GuitarShop")
public class UserController {

    @GetMapping("/public")
    public String register() {
        return "Im here to sign up !";
    }
}
