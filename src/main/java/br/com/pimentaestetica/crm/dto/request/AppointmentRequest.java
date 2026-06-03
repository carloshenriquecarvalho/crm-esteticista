package br.com.pimentaestetica.crm.dto.request;

import java.time.LocalDateTime;

public record AppointmentRequest(
        String title,
        String availability,
        LocalDateTime dateTimeStart
        ) {
}
