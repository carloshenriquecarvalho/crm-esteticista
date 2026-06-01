package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.config.JWTTokenConfig;
import br.com.pimentaestetica.crm.dto.request.LoginRequest;
import br.com.pimentaestetica.crm.dto.request.RegisterUserRequest;
import br.com.pimentaestetica.crm.dto.response.LoginResponse;
import br.com.pimentaestetica.crm.dto.response.RegisterUserResponse;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.model.user.UserRole;
import br.com.pimentaestetica.crm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenConfig tokenConfig;

    // Construtor padrão limpo (Sem duplicidade manual)
    public AuthController(){}

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        var userAndPasswordToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(userAndPasswordToken);

        User user = (User) authentication.getPrincipal();
        String token = tokenConfig.generateToken(user);

        // Retorno populado corretamente com o DTO
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request){
        User user = new User();
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setName(request.name());

        Set<UserRole> defaultRoles = new HashSet<>();
        defaultRoles.add(UserRole.ROLE_ADMIN);
        user.setRoles(defaultRoles);

        User savedUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterUserResponse(savedUser.getName(), savedUser.getEmail()));
    }
}