package br.com.pimentaestetica.crm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record BeauticianRequest(
        @NotNull
        @Schema(example = "Dra. Fernanda")
        String name,

        @NotNull
        @Schema(example = "drafernanda@gmail.com")
        String email) {
}
