package br.com.pimentaestetica.crm.service;

import br.com.pimentaestetica.crm.model.procedure.Procedure;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.repository.ProcedureRepository;
import br.com.pimentaestetica.crm.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

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
    public Procedure createProcedure(Procedure procedure, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.addProcedure(procedure);

        procedureRepository.save(procedure);
        return procedure;
    }

    // Read
    // Get All Procedures
    public List<Procedure> getAllProcedures(UUID userId) {
        return procedureRepository.findAllByUserId(userId, Sort.by("name").ascending());
    }

    // Get procedure by id
    public Optional<Procedure> getProcedureById(UUID procedureId, UUID userId) {
        return procedureRepository.findByIdAndUserId(procedureId, userId);
    }

    // Update procedure by id
    @Transactional
    public Procedure updateProcedureById(UUID procedureId, Procedure procedureData, UUID userId) {
        Procedure procedure = procedureRepository.findByIdAndUserId(procedureId, userId)
                .orElseThrow(() -> new RuntimeException("Procedimento não encontrado"));

        procedure.setName(procedureData.getName());
        procedure.setValue(procedureData.getValue());
        procedure.setActive(procedureData.getActive());

        return procedureRepository.save(procedure);
    }

    // Delete procedure by id
    @Transactional
    public void deleteProcedureById(UUID procedureId, UUID userId) {
        Procedure procedure = procedureRepository.findByIdAndUserId(procedureId, userId).orElseThrow(() -> new RuntimeException("Procedimento não existe."));

        procedureRepository.delete(procedure);
    }

}
