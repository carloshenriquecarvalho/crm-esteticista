package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.procedure.Procedure;
import br.com.pimentaestetica.crm.service.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/procedure")
public class ProcedureController {

    @Autowired
    private ProcedureService procedureService;

    // Create - Corrigido: userId agora vem como Query Param
    @PostMapping
    public ResponseEntity<Procedure> add(@RequestBody Procedure procedure, @RequestParam UUID userId) {
        Procedure created = procedureService.createProcedure(procedure, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get All by User
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Procedure>> getAll(@PathVariable UUID userId) {
        List<Procedure> procedures = procedureService.getAllProcedures(userId);
        return ResponseEntity.ok(procedures);
    }

    // Get by Id - Tratado Optional
    @GetMapping("/{id}")
    public ResponseEntity<Procedure> getById(@PathVariable UUID id) {
        return procedureService.getProcedureById(id)
                .map(procedure -> ResponseEntity.ok().body(procedure))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Procedure> update(@PathVariable UUID id, @RequestBody Procedure procedure) {
        Procedure updated = procedureService.updateProcedureById(id, procedure);
        return ResponseEntity.ok(updated);
    }

    // Delete - Retorna 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        procedureService.deleteProcedureById(id);
        return ResponseEntity.noContent().build();
    }
}