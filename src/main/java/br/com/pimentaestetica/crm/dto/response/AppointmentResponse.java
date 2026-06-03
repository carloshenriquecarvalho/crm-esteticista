package br.com.pimentaestetica.crm.dto.response;

import br.com.pimentaestetica.crm.model.appointment.Appointment;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponse(
        UUID id,
        String title,
        LocalDateTime dateTimeStart,
        String beauticianName,
        String patientName,
        String procedureName,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal value

) {
    public AppointmentResponse(Appointment appointment) {
        this(
                appointment.getId(),
                appointment.getTitle(),
                appointment.getDateTimeStart(),
                appointment.getBeautician().getName(),
                appointment.getPatient().getName(),
                appointment.getProcedure().getName(),
                appointment.getProcedure().getValue()
        );
    }
}
