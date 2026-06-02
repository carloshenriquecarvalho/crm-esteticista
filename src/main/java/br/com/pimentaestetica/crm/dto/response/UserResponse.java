package br.com.pimentaestetica.crm.dto.response;

import br.com.pimentaestetica.crm.model.user.User;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email
) {

    public UserResponse(User user) {
        this(user.getId(), user.getName(), user.getEmail());
    }
}