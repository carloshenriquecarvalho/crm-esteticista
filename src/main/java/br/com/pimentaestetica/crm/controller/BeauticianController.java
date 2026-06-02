package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.model.appointment.Appointment;
import br.com.pimentaestetica.crm.model.beautician.Beautician;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.service.BeauticianService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/beautician")
public class BeauticianController {

    @Autowired
    private BeauticianService beauticianService;

    @PostMapping
    public ResponseEntity<Beautician> add(@RequestBody Beautician beautician, @AuthenticationPrincipal User user) {
        Beautician created = beauticianService.createBeautician(beautician, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get All by User
    @GetMapping("/all")
    public ResponseEntity<List<Beautician>> getAll(@AuthenticationPrincipal User user) {
        List<Beautician> beauticians = beauticianService.getAllBeauticians(user.getId());
        return ResponseEntity.ok(beauticians);
    }

    // Get by Id
    @GetMapping("/{beauticianId}")
    public ResponseEntity<Beautician> getById(@PathVariable UUID beauticianId, @AuthenticationPrincipal User user) {
        return beauticianService.getBeauticianById(beauticianId, user.getId())
                .map(beautician -> ResponseEntity.ok().body(beautician))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{beauticianId}")
    public ResponseEntity<Beautician> update(@PathVariable UUID beauticianId, @RequestBody Beautician beautician, @AuthenticationPrincipal User user) {
        Beautician updatedBeautician = beauticianService.updateBeauticianById(
                user.getId(),
                beautician,
                beauticianId
        );
        return ResponseEntity.ok(updatedBeautician);
    }

    // Delete - Retorna 204 No Content
    @DeleteMapping("/{beauticianId}")
    public ResponseEntity<Void> delete(@PathVariable UUID beauticianId, @AuthenticationPrincipal User user) {
        beauticianService.deleteBeauticianById(beauticianId, user.getId());
        return ResponseEntity.noContent().build();
    }
}