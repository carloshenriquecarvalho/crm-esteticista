package br.com.pimentaestetica.crm.dto.response;

import br.com.pimentaestetica.crm.model.beautician.Beautician;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record BeauticianResponse(
        @Schema(example = "3fa85f64-5717-4562-b9gc-2c963f66afa6")
        UUID id,

        @Schema(example = "Dra Fernanda")
        String name,

        @Schema(example = "drafernanda@gmail.com")
        String email
) {
    public BeauticianResponse(Beautician beautician) {
        this (
                beautician.getId(),
                beautician.getName(),
                beautician.getEmail()
        );
    }
}
