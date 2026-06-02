package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.service.PatientService;
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
@RequestMapping("/api/patient")
@Tag(name = "Pacientes", description = "Endpoints para gerenciamento de pacientes.")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    @Operation(summary = "Cria um novo paciente seguro.", description = "Gera um novo paciente atrelado ao usuário extraído do JWT.")
    public ResponseEntity<Patient> add(@RequestBody Patient patient, @AuthenticationPrincipal User user) {
        Patient created = patientService.createPatient(patient, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get All by User
    @GetMapping("/all")
    @Operation(summary = "Recebe todos os pacientes.", description = "Retorna todos os pacientes que estão atrelados ao usuário extraído do JWT.")
    public ResponseEntity<List<Patient>> getAll(@AuthenticationPrincipal User user) {
        List<Patient> patients = patientService.getAllPatients(user);
        return ResponseEntity.ok(patients);
    }

    // Get by Id
    @GetMapping("/{patientId}")
    @Operation(summary = "Recebe apenas um paciente.", description = "Retorna um único paciente baseado no id do paciente e que esteja atrelada ao id do usuário extraído do JWT.")
    public ResponseEntity<Patient> getById(@PathVariable UUID patientId, @AuthenticationPrincipal User user) {
        return patientService.getPatientById(patientId, user.getId())
                .map(patient -> ResponseEntity.ok().body(patient))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping
    @Operation(summary = "Atualiza um paciente.", description = "Retorna um único paciente atualizado a partir do id do paciente e do id do usuário extraído do JWT.")
    public ResponseEntity<Patient> update(@PathVariable UUID patientId, @AuthenticationPrincipal User user, @RequestBody Patient patient) {
        Patient updated = patientService.updatePatientById(user.getId(), patientId, patient);
        return ResponseEntity.ok(updated);
    }

    // Delete
    @DeleteMapping("/{patientId}")
    @Operation(summary = "Deleta um paciente.", description = "Deleta o paciente através do seu id e que esteja atrelada ao usuário.")
    public ResponseEntity<Void> delete(@PathVariable UUID patientId, @AuthenticationPrincipal User user) {
        patientService.deletePatientById(patientId, user.getId());
        return ResponseEntity.noContent().build();
    }
}