package br.com.pimentaestetica.crm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(
        @Schema(example = "Carlos Henrique")
        @NotEmpty(message = "Nome é obrigatório")
        String name,

        @Schema(example = "carlos@gmail.com")
        @NotEmpty(message = "Email é obrigatório")
        String email,

        @Schema(example = "senhaSegura123")
        @NotEmpty(message = "Senha é obrigatória")
        String password){}
