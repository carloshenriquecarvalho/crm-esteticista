package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user")
    public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public User addUser(@RequestBody User user){
        return userService.createUser(user);

    }
}
