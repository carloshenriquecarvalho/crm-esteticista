package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user")
@Tag(name = "Usuário", description = "Endpoints para gerenciamento de usuários.")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    @Operation(summary = "Cria um novo usuário.", description = "Gera um usuário.")
    public User addUser(@RequestBody User user){
        return userService.createUser(user);
    }
}
