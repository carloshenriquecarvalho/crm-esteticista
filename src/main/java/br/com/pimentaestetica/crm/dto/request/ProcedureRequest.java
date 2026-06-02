package br.com.pimentaestetica.crm.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProcedureRequest(
        @NotBlank(message = "Nome do procedimento é obrigatório")
        String name,

        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal value,

        @NotNull(message = "Duração é obrigatória")
        Integer durationMinutes
) {}