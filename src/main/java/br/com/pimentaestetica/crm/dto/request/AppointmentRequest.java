package br.com.pimentaestetica.crm.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequest(
        @NotBlank(message = "O título é obrigatório")
        String title,

        @NotNull(message = "O ID da esteticista é obrigatório")
        UUID beauticianId,

        @NotNull(message = "O ID do paciente é obrigatório")
        UUID patientId,

        @NotNull(message = "O ID do procedimento é obrigatório")
        UUID procedureId,

        @NotNull(message = "A data e hora são obrigatórias")
        @Future(message =  "O agendamento deve ser feito em uma data futura")
        LocalDateTime dateTimeStart
        ) {
}
