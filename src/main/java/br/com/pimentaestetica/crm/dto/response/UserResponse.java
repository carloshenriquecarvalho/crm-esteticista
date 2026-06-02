package br.com.pimentaestetica.crm.dto.response;

import br.com.pimentaestetica.crm.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record UserResponse(
        @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(example = "Carlos")
        String name,

        @Schema(example = "carlos@gmail.com")
        String email
) {

    public UserResponse(User user) {
        this(user.getId(), user.getName(), user.getEmail());
    }
}