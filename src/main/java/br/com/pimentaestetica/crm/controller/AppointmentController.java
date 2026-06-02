package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.appointment.Appointment;

import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    // Crud
    // Create Patient
    @PostMapping
    public ResponseEntity<Appointment> create(@RequestBody Appointment appointment, @AuthenticationPrincipal User user) {
        Appointment createdAppointment = appointmentService.createAppointment(
                appointment,
                user.getId(),
                appointment.getPatient().getId(),
                appointment.getBeautician().getId(),
                appointment.getProcedure().getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    // Get all appointments by user id
    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> getAll(@AuthenticationPrincipal User user) {
        List<Appointment> appointments = appointmentService.getAllAppointments(user.getId());
        return ResponseEntity.ok(appointments);
    }

    // Get appointment by id
    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getById(@PathVariable UUID appointmentId, @AuthenticationPrincipal User user) {
        return appointmentService.getAppointmentById(appointmentId, user.getId())
                .map(appointment -> ResponseEntity.ok().body(appointment))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update appointment
    @PutMapping("/{appointmentId}")
    public ResponseEntity<Appointment> update(@PathVariable UUID appointmentId, @AuthenticationPrincipal User user, @RequestBody Appointment appointment) {
        Appointment updatedAppointment = appointmentService.updateAppointmentById(
                user.getId(),
                appointmentId,
                appointment,
                appointment.getPatient() != null ? appointment.getPatient().getId() : null,
                appointment.getBeautician() != null ? appointment.getBeautician().getId() : null,
                appointment.getProcedure() != null ? appointment.getProcedure().getId() : null
        );

        return ResponseEntity.ok(updatedAppointment);
    }

    // Delete appointment
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> delete(@PathVariable UUID appointmentId, @AuthenticationPrincipal User user) {
        appointmentService.deleteAppointmentById(appointmentId, user.getId());
        return ResponseEntity.noContent().build();
    }
}
