package br.com.pimentaestetica.crm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record PatientRequest(
        @Schema(example = "Natalia")
        String name,

        @Schema(example = "natalia@gmail.com")
        String email,

        @Schema(example = "61999999999")
        String phoneNumber) {
}
