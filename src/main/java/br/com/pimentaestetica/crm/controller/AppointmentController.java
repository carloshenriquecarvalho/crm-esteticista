package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.appointment.Appointment;

import br.com.pimentaestetica.crm.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Appointment> create(@RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.createAppointment(
                appointment,
                appointment.getUser().getId(),
                appointment.getPatient().getId(),
                appointment.getBeautician().getId(),
                appointment.getProcedure().getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    // Get all appointments by user id
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Appointment>> getAll(@PathVariable UUID userId) {
        List<Appointment> appointments = appointmentService.getAllAppointments(userId);
        return ResponseEntity.ok(appointments);
    }

    // Get appointment by id
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable UUID id) {
        return appointmentService.getAppointmentById(id)
                .map(appointment -> ResponseEntity.ok().body(appointment))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update appointment
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(@PathVariable UUID id, @RequestBody Appointment appointment) {
        Appointment updatedAppointment = appointmentService.updateAppointmentById(
                id,
                appointment,
                appointment.getPatient().getId(),
                appointment.getBeautician().getId(),
                appointment.getProcedure().getId()
        );

        return ResponseEntity.ok(updatedAppointment);
    }

    // Delete appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        appointmentService.deleteAppointmentById(id);
        return ResponseEntity.noContent().build();
    }
}
