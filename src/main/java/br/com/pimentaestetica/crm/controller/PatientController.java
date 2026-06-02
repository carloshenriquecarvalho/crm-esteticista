package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> add(@RequestBody Patient patient, @AuthenticationPrincipal User user) {
        Patient created = patientService.createPatient(patient, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get All by User
    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getAll(@AuthenticationPrincipal User user) {
        List<Patient> patients = patientService.getAllPatients(user);
        return ResponseEntity.ok(patients);
    }

    // Get by Id
    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> getById(@PathVariable UUID patientId, @AuthenticationPrincipal User user) {
        return patientService.getPatientById(patientId, user.getId())
                .map(patient -> ResponseEntity.ok().body(patient))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping
    public ResponseEntity<Patient> update(@PathVariable UUID patientId, @AuthenticationPrincipal User user, @RequestBody Patient patient) {
        Patient updated = patientService.updatePatientById(user.getId(), patientId, patient);
        return ResponseEntity.ok(updated);
    }

    // Delete - Retorna 204 No Content
    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> delete(@PathVariable UUID patientId, @AuthenticationPrincipal User user) {
        patientService.deletePatientById(patientId, user.getId());
        return ResponseEntity.noContent().build();
    }
}