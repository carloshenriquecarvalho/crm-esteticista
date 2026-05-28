package br.com.pimentaestetica.crm.model.user;

import br.com.pimentaestetica.crm.model.appoinment.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public User addUser(@RequestBody User user){
        return userService.createUser(user);

    }
}
