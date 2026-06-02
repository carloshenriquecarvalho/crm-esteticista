package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.config.JWTTokenConfig;
import br.com.pimentaestetica.crm.dto.request.LoginRequest;
import br.com.pimentaestetica.crm.dto.request.RegisterUserRequest;
import br.com.pimentaestetica.crm.dto.response.LoginResponse;
import br.com.pimentaestetica.crm.dto.response.RegisterUserResponse;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação de usuário.")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenConfig tokenConfig;

    @PostMapping("/login")
    @Operation(summary = "Recebe credenciais de login do usuário.", description = "Retorna um token JWT para validação de segurança.")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        var userAndPasswordToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(userAndPasswordToken);

        User user = (User) authentication.getPrincipal();
        String token = tokenConfig.generateToken(user);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Recebe credenciais de registro do usuário.", description = "Gera um novo usuário administrativo.")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        User savedUser = userService.registerNewUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterUserResponse(savedUser.getName(), savedUser.getEmail()));
    }
}