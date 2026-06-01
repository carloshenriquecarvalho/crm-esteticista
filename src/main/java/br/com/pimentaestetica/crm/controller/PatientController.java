package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Create - Corrigido: userId agora vem como Query Param
    @PostMapping
    public ResponseEntity<Patient> add(@RequestBody Patient patient, @RequestParam UUID userId) {
        Patient created = patientService.createPatient(patient, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get All by User
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Patient>> getAll(@PathVariable UUID userId) {
        List<Patient> patients = patientService.getAllPatients(userId);
        return ResponseEntity.ok(patients);
    }

    // Get by Id - Tratado Optional
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getById(@PathVariable UUID id) {
        return patientService.getPatientById(id)
                .map(patient -> ResponseEntity.ok().body(patient))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable UUID id, @RequestBody Patient patient) {
        Patient updated = patientService.updatePatientById(id, patient);
        return ResponseEntity.ok(updated);
    }

    // Delete - Retorna 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }
}