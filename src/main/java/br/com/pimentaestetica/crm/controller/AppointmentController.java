package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.dto.request.AppointmentRequest;
import br.com.pimentaestetica.crm.dto.response.AppointmentResponse;
import br.com.pimentaestetica.crm.model.appointment.Appointment;

import br.com.pimentaestetica.crm.model.appointment.AppointmentAvailability;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Agendamentos", description = "Endpoints para gerenciamento da agenda da clínica.")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    // Crud
    // Create Patient
    @PostMapping
    @Operation(summary = "Cria um novo agendamento seguro.", description = "Gera um agendamento atrelado ao usuário extraído do JWT.")
    public ResponseEntity<AppointmentResponse> create(@RequestBody AppointmentRequest appointmentRequest, @AuthenticationPrincipal User user) {

        Appointment createdAppointment = appointmentService.createAppointment(appointmentRequest, user.getId(), appointmentRequest.patientId(), appointmentRequest.beauticianId(), appointmentRequest.procedureId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentResponse(createdAppointment));
    }

    // Get all appointments by user id
    @GetMapping("/all")
    @Operation(summary = "Recebe todos os agendamentos.", description = "Retorna todos os agendamentos que estão atrelados ao usuário extraído do JWT.")
    public ResponseEntity<List<AppointmentResponse>> getAll(@AuthenticationPrincipal User user) {
        List<AppointmentResponse> appointmentResponses = appointmentService.getAllAppointments(user.getId()).stream().map(AppointmentResponse::new).toList();
        return ResponseEntity.ok(appointmentResponses);
    }

    // Get appointment by id
    @GetMapping("/{appointmentId}")
    @Operation(summary = "Recebe apenas um agendamento.", description = "Retorna um único agendamento baseado no id do agendamento e que esteja atrelado ao id do usuário extraído do JWT.")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable UUID appointmentId, @AuthenticationPrincipal User user) {
        AppointmentResponse appointmentResponse = new AppointmentResponse(appointmentService.getAppointmentById(appointmentId, user.getId()).orElseThrow(() -> new RuntimeException("Agendamento não encontrado.")));

        return ResponseEntity.ok(appointmentResponse);
    }

    // Update appointment
    @PutMapping("/{appointmentId}")
    @Operation(summary = "Atualiza um agendamento.", description = "Retorna um único agendamento atualizado a partir do id do agendamento e do id do usuário extraído do JWT.")
    public ResponseEntity<AppointmentResponse> update(@PathVariable UUID appointmentId, @AuthenticationPrincipal User user, @RequestBody AppointmentRequest appointmentRequest) {

        Appointment appointment = new Appointment();

        appointment.setAppointmentAvailability(AppointmentAvailability.UNPAID);
        appointment.setDateTimeStart(appointmentRequest.dateTimeStart());
        appointment.setTitle(appointmentRequest.title());


        Appointment updatedAppointment = appointmentService.updateAppointmentById(
                user.getId(),
                appointmentId,
                appointment,
                appointment.getPatient() != null ? appointment.getPatient().getId() : null,
                appointment.getBeautician() != null ? appointment.getBeautician().getId() : null,
                appointment.getProcedure() != null ? appointment.getProcedure().getId() : null
        );

        return ResponseEntity.ok(new AppointmentResponse(updatedAppointment));
    }

    // Delete appointment
    @DeleteMapping("/{appointmentId}")
    @Operation(summary = "Deleta um agendamento.", description = "Deleta o agendamento através do seu id e que esteja atrelado ao usuário.")
    public ResponseEntity<Void> delete(@PathVariable UUID appointmentId, @AuthenticationPrincipal User user) {
        appointmentService.deleteAppointmentById(appointmentId, user.getId());
        return ResponseEntity.noContent().build();
    }
}
