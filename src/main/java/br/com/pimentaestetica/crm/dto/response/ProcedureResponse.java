package br.com.pimentaestetica.crm.dto.response;

import br.com.pimentaestetica.crm.model.procedure.Procedure;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.UUID;

public record ProcedureResponse(UUID id, String name, @JsonFormat(shape = JsonFormat.Shape.STRING)BigDecimal value, Integer durationMinutes, String availability) {
    public ProcedureResponse(Procedure procedure) {
        this(
                procedure.getId(),
                procedure.getName(),
                procedure.getValue(),
                procedure.getDurationMinutes(),
                procedure.getProcedureAvailability().name()
        );
    }
}