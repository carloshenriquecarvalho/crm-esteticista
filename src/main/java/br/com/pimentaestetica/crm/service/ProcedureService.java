package br.com.pimentaestetica.crm.service;

import br.com.pimentaestetica.crm.dto.request.ProcedureRequest;
import br.com.pimentaestetica.crm.dto.response.ProcedureResponse;
import br.com.pimentaestetica.crm.model.procedure.Procedure;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.repository.ProcedureRepository;
import br.com.pimentaestetica.crm.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private UserRepository userRepository;

    // Create
    @Transactional
    public Procedure createProcedure(ProcedureRequest procedureRequest, UUID userId) {
        Procedure procedure = new Procedure();

        procedure.setName(procedureRequest.name());
        procedure.setValue(procedureRequest.value());
        procedure.setDurationMinutes(procedureRequest.durationMinutes());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.addProcedure(procedure);

        procedureRepository.save(procedure);
        return procedure;
    }

    // Read
    // Get All Procedures
    public List<ProcedureResponse> getAllProcedures(UUID userId) {
        return  procedureRepository.findAllByUserId(userId, Sort.by("name").ascending()).stream()
                .map(ProcedureResponse::new)
                .toList();
    }

    // Get procedure by id
    public ProcedureResponse getProcedureById(UUID procedureId, UUID userId) {
        return new ProcedureResponse(procedureRepository.findByIdAndUserId(procedureId, userId).orElseThrow(() -> new RuntimeException("Procedimento não encontrado")));
    }

    // Update procedure by id
    @Transactional
    public ProcedureResponse updateProcedureById(UUID procedureId, ProcedureRequest procedureData, UUID userId) {
        Procedure procedure = procedureRepository.findByIdAndUserId(procedureId, userId)
                .orElseThrow(() -> new RuntimeException("Procedimento não encontrado"));

        procedure.setName(procedureData.name());
        procedure.setValue(procedureData.value());
        procedure.setDurationMinutes(procedureData.durationMinutes());
        procedureRepository.save(procedure);

        return new ProcedureResponse(procedure);
    }

    // Delete procedure by id
    @Transactional
    public void deleteProcedureById(UUID procedureId, UUID userId) {
        Procedure procedure = procedureRepository.findByIdAndUserId(procedureId, userId).orElseThrow(() -> new RuntimeException("Procedimento não existe."));

        procedureRepository.delete(procedure);
    }

}
