package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.dto.request.PatientRequest;
import br.com.pimentaestetica.crm.dto.response.PatientResponse;
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
    public ResponseEntity<PatientResponse> add(@RequestBody PatientRequest patientRequest, @AuthenticationPrincipal User user) {
        Patient patient = patientService.createPatient(patientRequest, user.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new PatientResponse(patient));
    }

    // Get All by User
    @GetMapping("/all")
    @Operation(summary = "Recebe todos os pacientes.", description = "Retorna todos os pacientes que estão atrelados ao usuário extraído do JWT.")
    public ResponseEntity<List<PatientResponse>> getAll(@AuthenticationPrincipal User user) {
        List<PatientResponse> patientResponses = patientService.getAllPatients(user).stream().map(PatientResponse::new).toList();
        return ResponseEntity.ok(patientResponses);
    }

    // Get by Id
    @GetMapping("/{patientId}")
    @Operation(summary = "Recebe apenas um paciente.", description = "Retorna um único paciente baseado no id do paciente e que esteja atrelada ao id do usuário extraído do JWT.")
    public ResponseEntity<PatientResponse> getById(@PathVariable UUID patientId, @AuthenticationPrincipal User user) {
        Patient patient =  patientService.getPatientById(user.getId(), patientId);

        return ResponseEntity.ok(new PatientResponse(patient));
    }

    // Update
    @PutMapping
    @Operation(summary = "Atualiza um paciente.", description = "Retorna um único paciente atualizado a partir do id do paciente e do id do usuário extraído do JWT.")
    public ResponseEntity<PatientResponse> update(@PathVariable UUID patientId, @AuthenticationPrincipal User user, @RequestBody PatientRequest patientRequest) {
        Patient updated = patientService.updatePatientById(user.getId(), patientId, patientRequest);


        return ResponseEntity.ok(new PatientResponse(updated));
    }

    // Delete
    @DeleteMapping("/{patientId}")
    @Operation(summary = "Deleta um paciente.", description = "Deleta o paciente através do seu id e que esteja atrelada ao usuário.")
    public ResponseEntity<Void> delete(@PathVariable UUID patientId, @AuthenticationPrincipal User user) {
        patientService.deletePatientById(patientId, user.getId());
        return ResponseEntity.noContent().build();
    }
}