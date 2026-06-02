package br.com.pimentaestetica.crm.service;

import br.com.pimentaestetica.crm.dto.request.RegisterUserRequest;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.model.user.UserRole;
import br.com.pimentaestetica.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerNewUser(RegisterUserRequest dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Este e-mail já está cadastrado.");
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());

        // Criptografia isolada na camada de negócio
        user.setPassword(passwordEncoder.encode(dto.password()));

        // Regra de negócio: Todo usuário auto-cadastrado começa como ADMIN
        Set<UserRole> defaultRoles = new HashSet<>();
        defaultRoles.add(UserRole.ROLE_ADMIN);
        user.setRoles(defaultRoles);

        return userRepository.save(user);
    }
}