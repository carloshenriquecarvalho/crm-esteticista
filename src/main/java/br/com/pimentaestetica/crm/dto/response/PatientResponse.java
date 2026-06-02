package br.com.pimentaestetica.crm.dto.response;

import br.com.pimentaestetica.crm.model.patient.Patient;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record PatientResponse(
        @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(example = "Juliana")
        String name,

        @Schema(example = "juliana@gmail")
        String email,

        @Schema(example = "61999999999")
        String phoneNumber

        ) {
    public PatientResponse(Patient patient) {
        this(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getPhoneNumber()
        );
    }
}
