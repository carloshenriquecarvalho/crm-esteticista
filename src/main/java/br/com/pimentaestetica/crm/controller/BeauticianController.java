package br.com.pimentaestetica.crm.controller;

import br.com.pimentaestetica.crm.dto.request.BeauticianRequest;
import br.com.pimentaestetica.crm.dto.response.BeauticianResponse;
import br.com.pimentaestetica.crm.model.appointment.Appointment;
import br.com.pimentaestetica.crm.model.beautician.Beautician;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.service.BeauticianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Esteticistas", description = "Endpoints para gerenciamento seguro das esteticistas.")
public class BeauticianController {

    @Autowired
    private BeauticianService beauticianService;

    @PostMapping
    @Operation(summary = "Cria uma nova esteticista segura.", description = "Gera uma esteticista atrelada ao usuário extraído do JWT.")
    public ResponseEntity<BeauticianResponse> add(@RequestBody BeauticianRequest beauticianRequest, @AuthenticationPrincipal User user) {
        Beautician created = beauticianService.createBeautician(beauticianRequest, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new BeauticianResponse(created));
    }

    // Get All by User
    @GetMapping("/all")
    @Operation(summary = "Recebe todas as esteticistas.", description = "Retorna todas as esteticistas que estão atreladas ao usuário extraído do JWT.")
    public ResponseEntity<List<BeauticianResponse>> getAll(@AuthenticationPrincipal User user) {
        List<BeauticianResponse> beauticianResponses = beauticianService.getAllBeauticians(user.getId()).stream().map(BeauticianResponse::new).toList();
        return ResponseEntity.ok(beauticianResponses);
    }

    // Get by Id
    @GetMapping("/{beauticianId}")
    @Operation(summary = "Recebe apenas uma esteticista.", description = "Retorna uma única esteticista baseado no id da esteticista e que esteja atrelada ao id do usuário extraído do JWT.")
    public ResponseEntity<BeauticianResponse> getById(@PathVariable UUID beauticianId, @AuthenticationPrincipal User user) {

        BeauticianResponse beauticianResponse = new BeauticianResponse(beauticianService.getBeauticianById(beauticianId, user.getId()));

        return ResponseEntity.ok(beauticianResponse);
    }

    // Update
    @PutMapping("/{beauticianId}")
    @Operation(summary = "Atualiza uma esteticista.", description = "Retorna uma única esteticista atualizada a partir do id da esteticista e do id do usuário extraído do JWT.")
    public ResponseEntity<BeauticianResponse> update(@PathVariable UUID beauticianId, @RequestBody BeauticianRequest beauticianRequest, @AuthenticationPrincipal User user) {

        Beautician beautician = new Beautician();

        beautician.setName(beauticianRequest.name());
        beautician.setEmail(beauticianRequest.email());

        Beautician updatedBeautician = beauticianService.updateBeauticianById(
                user.getId(),
                beautician,
                beauticianId
        );
        return ResponseEntity.ok(new BeauticianResponse(updatedBeautician));
    }

    // Delete4
    @DeleteMapping("/{beauticianId}")
    @Operation(summary = "Deleta uma esteticista.", description = "Deleta a esteticista através do seu id e que esteja atrelada ao usuário.")
    public ResponseEntity<Void> delete(@PathVariable UUID beauticianId, @AuthenticationPrincipal User user) {
        beauticianService.deleteBeauticianById(beauticianId, user.getId());
        return ResponseEntity.noContent().build();
    }
}