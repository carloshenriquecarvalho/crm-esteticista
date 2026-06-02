package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.procedure.Procedure;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.service.ProcedureService;
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
@RequestMapping("/api/procedure")
@Tag(name = "Procedimentos", description = "Endpoints para gerenciamento de procedimentos.")
public class ProcedureController {

    @Autowired
    private ProcedureService procedureService;

    // Create
    @PostMapping
    @Operation(summary = "Cria um novo procedimento seguro.", description = "Gera um novo procedimento atrelado ao usuário extraído do JWT.")
    public ResponseEntity<Procedure> add(@RequestBody Procedure procedure, @AuthenticationPrincipal User user) {
        Procedure created = procedureService.createProcedure(procedure, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get All by User
    @GetMapping("/all")
    @Operation(summary = "Recebe todos os procedimentos.", description = "Retorna todos os procedimentos que estão atrelados ao usuário extraído do JWT.")
    public ResponseEntity<List<Procedure>> getAll(@AuthenticationPrincipal User user) {
        List<Procedure> procedures = procedureService.getAllProcedures(user.getId());
        return ResponseEntity.ok(procedures);
    }

    // Get by Id
    @GetMapping("/{procedureId}")
    @Operation(summary = "Recebe apenas um procedimento.", description = "Retorna um único procedimento baseado no id do procedimento e que esteja atrelada ao id do usuário extraído do JWT.")
    public ResponseEntity<Procedure> getById(@PathVariable UUID procedureId, @AuthenticationPrincipal User user) {
        return procedureService.getProcedureById(procedureId, user.getId())
                .map(procedure -> ResponseEntity.ok().body(procedure))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{procedureId}")
    @Operation(summary = "Atualiza um procedimento.", description = "Retorna um único procedimento atualizado a partir do id do procedimento e do id do usuário extraído do JWT.")
    public ResponseEntity<Procedure> update(@PathVariable UUID procedureId, @RequestBody Procedure procedure, @AuthenticationPrincipal User user) {
        Procedure updated = procedureService.updateProcedureById(procedureId, procedure, user.getId());
        return ResponseEntity.ok(updated);
    }

    // Delete
    @Operation(summary = "Deleta um procedimento.", description = "Deleta o procedimento através do seu id e que esteja atrelada ao usuário.")
    @DeleteMapping("/{procedureId}")
    public ResponseEntity<Void> delete(@PathVariable UUID procedureId, @AuthenticationPrincipal User user) {
        procedureService.deleteProcedureById(procedureId, user.getId());
        return ResponseEntity.noContent().build();
    }
}