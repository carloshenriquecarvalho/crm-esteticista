package br.com.pimentaestetica.crm.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
        @NotBlank(message = "O nome não pode ser vazio")
        String name
) {}