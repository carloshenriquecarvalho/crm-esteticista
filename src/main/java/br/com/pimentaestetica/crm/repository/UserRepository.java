package br.com.pimentaestetica.crm.repository;

import br.com.pimentaestetica.crm.model.user.User;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<UserDetails> findUserByEmail(String userName);

    boolean existsByEmail(@NotEmpty(message = "Email é obrigatório") String email);
}
