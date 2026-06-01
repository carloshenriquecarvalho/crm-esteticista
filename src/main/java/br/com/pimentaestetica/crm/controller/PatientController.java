package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Patient add(@RequestBody Patient patient){
        return patientService.createPatient(patient);
    }

    // Get All patients
    @GetMapping
    public List<Patient> getAll(){
        return patientService.getAllPatients();
    }

    // Get patient by id
    @GetMapping("/{id}")
    public Optional<Patient> getById(@PathVariable UUID id){
        return patientService.getPatientById(id);
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
