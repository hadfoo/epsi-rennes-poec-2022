package fr.rennes.epsi.poec.bob.guitarshop.Controller;

import fr.rennes.epsi.poec.bob.guitarshop.Domain.user.User;
import fr.rennes.epsi.poec.bob.guitarshop.Exception.TechnicalException;
import fr.rennes.epsi.poec.bob.guitarshop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/GuitarShop")
public class UserController {

    @GetMapping("/public")
    public String register() {
        return "Im here to sign up !";
    }

    @Autowired
    private UserService userService;

    @PostMapping("/public/register")

//    # curl -d "param1=value1&param2=value2" -X POST http://localhost:3000/data
//    POST http://localhost:1969/public/register
//    Content-Type: application/x-www-form-urlencoded
//    param1=value1&param2=value2
//    ###
    public void addUser(User user) {
        try {
            userService.registerUser(user);
        } catch (TechnicalException e) {
            throw new TechnicalException(new SQLException("public/register route user.service.addUser failed"));
        }
    }
}
