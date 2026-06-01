package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.dto.request.LoginRequest;
import br.com.pimentaestetica.crm.dto.request.RegisterUserRequest;
import br.com.pimentaestetica.crm.dto.response.LoginResponse;
import br.com.pimentaestetica.crm.dto.response.RegisterUserResponse;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.repository.UserRepository;
import br.com.pimentaestetica.crm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public AuthController(){}

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        return null;

    }

    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request){
        User user = new User();
        user.setPassword(request.password());
        user.setEmail(request.email());
        user.setName(request.name());
        
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUserResponse(user.getUsername(), user.getEmail()));
    }
}
