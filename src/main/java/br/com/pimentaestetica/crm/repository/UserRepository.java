package br.com.pimentaestetica.crm.repository;

import br.com.pimentaestetica.crm.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
