package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.beautician.Beautician;
import br.com.pimentaestetica.crm.service.BeauticianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/beautician")
public class BeauticianController {

    @Autowired
    private BeauticianService beauticianService;

    // Create - Corrigido: userId agora vem como Query Param (?userId=...)
    @PostMapping
    public ResponseEntity<Beautician> add(@RequestBody Beautician beautician, @RequestParam UUID userId) {
        Beautician created = beauticianService.createBeautician(beautician, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get All by User
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Beautician>> getAll(@PathVariable UUID userId) {
        List<Beautician> beauticians = beauticianService.getAllBeauticians(userId);
        return ResponseEntity.ok(beauticians);
    }

    // Get by Id - Tratado Optional
    @GetMapping("/{id}")
    public ResponseEntity<Beautician> getById(@PathVariable UUID id) {
        return beauticianService.getBeauticianById(id)
                .map(beautician -> ResponseEntity.ok().body(beautician))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Beautician> update(@PathVariable UUID id, @RequestBody Beautician beautician) {
        Beautician updated = beauticianService.updateBeauticianById(id, beautician);
        return ResponseEntity.ok(updated);
    }

    // Delete - Retorna 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        beauticianService.deleteBeauticianById(id);
        return ResponseEntity.noContent().build();
    }
}