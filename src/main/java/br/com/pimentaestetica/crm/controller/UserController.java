package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.dto.request.RegisterUserRequest;
import br.com.pimentaestetica.crm.dto.response.UserResponse;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Cria um novo usuário administrativo", description = "Gera um usuário no banco e retorna seus dados sem a senha.")
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody RegisterUserRequest request) {

        User savedUser = userService.registerNewUser(request);

        UserResponse userResponse = new UserResponse(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}