package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Crud
    // Create Patient
    @PostMapping
    public Patient add(@RequestBody Patient patient, @RequestBody UUID id){
        return patientService.createPatient(patient, id);
    }

    // Get All patients
    @GetMapping("/{id}/patient")
    public List<Patient> getAll(@PathVariable UUID id){
        return patientService.getAllPatients(id);
    }

    // Get patient by id
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Patient>> getById(@PathVariable UUID id){
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    // Update patient
    @PutMapping("/{id}")
    public Patient update(@PathVariable UUID id, @RequestBody Patient patient) {
        return patientService.updatePatientById(patient.getId(), patient);
    }

    // Delete patient
    @DeleteMapping
    public boolean delete(@PathVariable UUID id) {
        return patientService.deletePatientById(id);
    }

}
