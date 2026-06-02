package br.com.pimentaestetica.crm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
        @NotBlank(message = "O nome não pode ser vazio")
        @Schema(example = "Carlos 2")
        String name
) {}