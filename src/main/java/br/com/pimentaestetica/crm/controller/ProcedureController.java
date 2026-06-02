package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.procedure.Procedure;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.service.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/procedure")
public class ProcedureController {

    @Autowired
    private ProcedureService procedureService;

    // Create
    @PostMapping
    public ResponseEntity<Procedure> add(@RequestBody Procedure procedure, @AuthenticationPrincipal User user) {
        Procedure created = procedureService.createProcedure(procedure, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get All by User
    @GetMapping("/all")
    public ResponseEntity<List<Procedure>> getAll(@AuthenticationPrincipal User user) {
        List<Procedure> procedures = procedureService.getAllProcedures(user.getId());
        return ResponseEntity.ok(procedures);
    }

    // Get by Id
    @GetMapping("/{procedureId}")
    public ResponseEntity<Procedure> getById(@PathVariable UUID procedureId, @AuthenticationPrincipal User user) {
        return procedureService.getProcedureById(procedureId, user.getId())
                .map(procedure -> ResponseEntity.ok().body(procedure))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{procedureId}")
    public ResponseEntity<Procedure> update(@PathVariable UUID procedureId, @RequestBody Procedure procedure, @AuthenticationPrincipal User user) {
        Procedure updated = procedureService.updateProcedureById(procedureId, procedure, user.getId());
        return ResponseEntity.ok(updated);
    }

    // Delete - Retorna 204 No Content
    @DeleteMapping("/{procedureId}")
    public ResponseEntity<Void> delete(@PathVariable UUID procedureId, @AuthenticationPrincipal User user) {
        procedureService.deleteProcedureById(procedureId, user.getId());
        return ResponseEntity.noContent().build();
    }
}